package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.TutorDistrictSelectMenuArgument
import ru.vega.telegram.model.menu.TutorTownSelectArgument
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService
import ru.vega.telegram.service.TownService

@Component
class TutorTownSelectMenu(
    private val townService: TownService,
    private val menuService: MenuService,
    private val menuProperties: MenuProperties,
    private val objectMapper: ObjectMapper,
    private val sessionService: SessionService
) : MenuHandler {

    companion object {
        const val ID = "ttsm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val (pageNumber, disciplineExternalId) = objectMapper.treeToValue<TutorTownSelectArgument>(callback)

        val pageable = Pageable(pageNumber, menuProperties.itemsPerPage)
        val page = townService.getTownPage(pageable)

        sessionService
            .getOrStartSession(message.user.id)
            .tutorDiscipline = disciplineExternalId

        return Menu(
            "Выбери город, в котором ты ищешь репетитора!",

            matrix {
                page.content.map {
                    menuService.makeGenericNextMenuButton(
                        it.title,
                        TutorDistrictSelectMenu.ID,
                        TutorDistrictSelectMenuArgument(0, it.externalId)
                    )
                }.forEach(::row)

                add(
                    menuService.makePagesNavigationMenu(page, ID) { TutorTownSelectArgument(it, disciplineExternalId) }
                )

                row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, TutorSelectDisciplineMenu.ID))
            }
        )
    }
}
