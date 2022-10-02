package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.TutorTownSelectArgument
import ru.vega.telegram.service.DisciplinesService
import ru.vega.telegram.service.MenuService

@Component
class TutorSelectDisciplineMenu(
    private val disciplinesService: DisciplinesService,
    private val menuService: MenuService
) : MenuHandler {

    companion object {
        const val ID = "tsdm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val menuMatrix = matrix<CallbackDataInlineKeyboardButton> {
            disciplinesService.getAll().map {
                menuService.makeGenericNextMenuButton(
                    it.title,
                    TutorTownSelectMenu.ID,
                    TutorTownSelectArgument(0, it.externalId)
                )
            }.chunked(3)
            .forEach { add(it) }

            row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, StartMenu.ID))
        }

        return Menu(
            "Выбери дисциплину, по которой ты ищешь репетитора",
            menuMatrix
        )
    }
}
