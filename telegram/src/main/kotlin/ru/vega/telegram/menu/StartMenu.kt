package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu

@Component
class StartMenu : MenuHandler {

    companion object {
        val MENU = Menu(
            """
                |Приветствую! Меня зовут Vega 💫
                |Я хочу помочь тебе в выборе твоей будущей специальности и ВУЗа! Что ты хочешь узнать?
            """.trimMargin(),

            matrix {
                row(
                    CallbackDataInlineKeyboardButton("Университеты", "1")
                )
                row(
                    CallbackDataInlineKeyboardButton("Предметы", "2"),
                    CallbackDataInlineKeyboardButton("Специальности", "3")
                )
                row(
                    CallbackDataInlineKeyboardButton("Репетиторы!", "4")
                )
                row(
                    CallbackDataInlineKeyboardButton("FAQ", "5")
                )
            }
        )
    }

    override val id: String = "st"

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu = MENU
}