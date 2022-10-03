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
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.TownService

@Component
class TutorDistrictSelectMenu(
    private val objectMapper: ObjectMapper,
    private val townService: TownService,
    private val menuProperties: MenuProperties,
    private val menuService: MenuService
) : MenuHandler {

    companion object {
        const val ID = "tdsm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val (pageNumber, townExternalId) = objectMapper.treeToValue<TutorDistrictSelectMenuArgument>(callback)

        val page = townService.getDistrictPage(townExternalId, Pageable(pageNumber, menuProperties.itemsPerPage))

        return Menu(
            "Выбери район города, в котором ты ищешь репетитора!",

            matrix {
                page.content.map {
                    menuService.makeGenericNextMenuButton(
                        it.title,
                        ID,
                        TutorDistrictSelectMenuArgument(0, it.externalId)
                    )
                }.forEach(::row)

                add(
                    menuService.makePagesNavigationMenu(page, TutorTownSelectMenu.ID) { TutorDistrictSelectMenuArgument(it, townExternalId) }
                )

                row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, TutorSelectDisciplineMenu.ID))
            }
        )
    }
}
