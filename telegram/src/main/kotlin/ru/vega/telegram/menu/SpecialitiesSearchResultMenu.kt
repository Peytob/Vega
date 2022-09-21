package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.PageSelectArguments
import ru.vega.telegram.service.DisciplinesSetService
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService
import ru.vega.telegram.service.UniversitySpecialityService

@Component
@EnableConfigurationProperties(MenuProperties::class)
class SpecialitiesSearchResultMenu(
    private val sessionService: SessionService,
    private val objectMapper: ObjectMapper,
    private val menuService: MenuService,
    private val disciplinesSetService: DisciplinesSetService
//    private val specialitiesService: SpecialitiesService
) : MenuHandler {

    companion object {
        const val ID = "ssr"
    }

    override val id: String = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val pageNumber = objectMapper
            .treeToValue<PageSelectArguments>(callback)
            .page

        val selectedDisciplines = sessionService
            .getOrStartSession(message.user.id)
            .selectedDisciplinesIds
            .toSet() // Should be immutable, because it will be used as key in cache

        val disciplinesSet = disciplinesSetService.getDisciplinesSet(selectedDisciplines)

        val page = if (disciplinesSet != null) {
            universitySpecialityService.getByDisciplinesSet(disciplinesSet, Pageable(pageNumber, menuProperties.itemsPerPage))
        } else {
            Page.empty()
        }

        return if (!page.empty) makeResultsExistsMenu(page) else makeResultsNotExistsMenu()
    }

    private fun makeResultsNotExistsMenu() = Menu(
        "Увы, но специальности по твоему запросу не нашлись :(",

        matrix {
            row(
                menuService.makeGenericNextMenuButton("Попробовать ввести другой набор!", SpecialityDisciplinesSelectMenu.ID))
        }
    )

    private fun makeResultsExistsMenu(page: Page<UniversitySpecialityDto>) = Menu(
        "Нашлись специальности, на которые можно поступить, сдавая выбранные тобой дисциплины",

        matrix {

            page.content.forEach {
                row(
                    menuService.makeGenericNextMenuButton(it.speciality.title, "--<><>")
                )
            }

            add(menuService.makePagesNavigationMenu(page, ID))

            row(
                menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, SpecialityDisciplinesSelectMenu.ID))
        }
    )
}
