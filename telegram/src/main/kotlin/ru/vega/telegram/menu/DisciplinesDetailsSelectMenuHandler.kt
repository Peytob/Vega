package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.DisciplineDetailsMenuArguments
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.service.DisciplinesService
import ru.vega.telegram.service.MenuService

@Component
class DisciplinesDetailsSelectMenuHandler(
    private val menuService: MenuService,
    private val disciplinesService: DisciplinesService
) : MenuHandler {

    companion object {
        const val ID = "dds"
    }

    override val id: String
        get() = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val menuMatrix = matrix<CallbackDataInlineKeyboardButton> {
            disciplinesService.getAll().map {
                menuService.makeGenericNextMenuButton(
                    it.title,
                    "todo",
                    DisciplineDetailsMenuArguments(it.externalId))
            }.chunked(3)
            .forEach { row(*it.toTypedArray()) }

            row(menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, StartMenu.ID))
        }

        return Menu("Какая дисциплина тебя интересует?", menuMatrix)
    }
}