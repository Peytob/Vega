package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.PageSelectArguments
import ru.vega.telegram.service.DisciplinesSetService
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService

@Component
class SpecialitiesSearchResult(
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
        val page = objectMapper
            .treeToValue<PageSelectArguments>(callback)
            .page

        val selectedDisciplines = sessionService
            .getOrStartSession(message.user.id)
            .selectedDisciplinesIds
            .toSet() // Should be immutable, because it will be used as key in cache

        val disciplinesSet = disciplinesSetService.getDisciplinesSet(selectedDisciplines)

        return Menu(
            "DisciplinesSet: $disciplinesSet",

            matrix {
                row(
                    menuService.makeGenericNextMenuButton("Start", StartMenu.ID))
            }
        )
    }
}
