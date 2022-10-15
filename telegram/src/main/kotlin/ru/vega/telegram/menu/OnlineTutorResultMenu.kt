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
import ru.vega.telegram.model.menu.*
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.TutorService

@Component
class OnlineTutorResultMenu(
    private val objectMapper: ObjectMapper,
    private val menuProperties: MenuProperties,
    private val menuService: MenuService,
    private val tutorService: TutorService,
) : MenuHandler {

    companion object {
        const val ID = "otr"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val (page, disciplineId) = objectMapper.treeToValue<OnlineTutorResultMenuArgument>(callback)

        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val tutorsPage = tutorService.getOnlineTutorsByDiscipline(disciplineId, pageable)

        return if (tutorsPage.empty) makeEmptyTutorsMenu() else Menu(
            "Выбери репетитора, который тебе интересен",

            matrix {
                tutorsPage.content.map {
                    menuService.makeGenericNextMenuButton(
                        it.name,
                        OnlineTutorDetailsMenu.ID,
                        OnlineTutorDetailsMenuArgument(it.externalId, page, disciplineId)
                    )
                }.forEach(::row)

                add(
                    menuService.makePagesNavigationMenu(tutorsPage, TutorTownSelectMenu.ID) { OnlineTutorResultMenuArgument(it, disciplineId) }
                )

                row(menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    OnlineTutorSelectDisciplineMenu.ID)
                )
            }
        )
    }

    private fun makeEmptyTutorsMenu() = Menu(
        "Нет репетиторов, принимающих онлайн, по твоему предмету :(",

        matrix {
            row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, OnlineTutorSelectDisciplineMenu.ID))
        }
    )
}
