package ru.vega.telegram.menu

import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.service.MenuService

@Component
class StartMenu(
    private val menuService: MenuService
) : StaticMenuHandler {

    companion object {
        const val ID = "s"
    }

    override val id: String = ID

    override val menu: Menu
        get() = Menu(
            """
            |Приветствую! Меня зовут Vega 💫
            |Я хочу помочь тебе в выборе твоей будущей специальности и ВУЗа! Что ты хочешь узнать?
        """.trimMargin(),

            matrix {
                row(
                    menuService.makeGenericNextMenuButton("Университеты", OtherTestMenu.ID)
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