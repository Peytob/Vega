package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu

@Component
class OtherTestMenu : MenuHandler {

    override val id: String
        get() = "otm"

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu = Menu(
        "test menu, nya",

        matrix {
            row(
                CallbackDataInlineKeyboardButton("Hiii", "Some callback")
            )
        }
    )
}