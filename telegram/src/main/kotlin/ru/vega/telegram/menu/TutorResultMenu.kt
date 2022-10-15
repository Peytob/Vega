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
import ru.vega.telegram.model.menu.TutorDetailsMenuArgument
import ru.vega.telegram.model.menu.TutorDistrictSelectMenuArgument
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService
import ru.vega.telegram.service.TutorService

@Component
class TutorResultMenu(
    private val sessionService: SessionService,
    private val objectMapper: ObjectMapper,
    private val menuService: MenuService,
    private val tutorService: TutorService,
    private val menuProperties: MenuProperties
) : MenuHandler {

    companion object {
        const val ID = "trm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val (page, districtId) = objectMapper.treeToValue<TutorDistrictSelectMenuArgument>(callback)

        val disciplineId = sessionService
            .getOrStartSession(message.user.id)
            .tutorDiscipline ?: throw IllegalStateException("Tutor result menu: tutor discipline not found")

        val townId = sessionService
            .getOrStartSession(message.user.id)
            .tutorTown ?: throw IllegalStateException("Tutor result menu: tutor town not found")

        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val tutorsPage = tutorService.getTutorsByDisciplineAndDistrict(disciplineId, districtId, pageable)

        return if (tutorsPage.empty) makeEmptyTutorsMenu(townId) else Menu(
            "Выбери репетитора, который тебе интересен",

            matrix {
                tutorsPage.content.map {
                    menuService.makeGenericNextMenuButton(
                        it.name,
                        TutorDetailsMenu.ID,
                        TutorDetailsMenuArgument(it.externalId)
                    )
                }.forEach(::row)

                add(
                    menuService.makePagesNavigationMenu(tutorsPage, TutorTownSelectMenu.ID) { TutorDistrictSelectMenuArgument(it, districtId) }
                )

                row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, TutorDistrictSelectMenu.ID, TutorDistrictSelectMenuArgument(0, townId)))
            }
        )
    }

    private fun makeEmptyTutorsMenu(townId: String) = Menu(
        "В выбранном тобой районе города нет ни одного подходящего репетитора :(",

        matrix {
            row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, TutorDistrictSelectMenu.ID, TutorDistrictSelectMenuArgument(0, townId)))
        }
    )
}
