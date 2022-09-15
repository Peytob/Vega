package ru.vega.telegram.menu

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.service.MenuService

@Component
class OtherTestMenu(
    private val menuService: MenuService
) : StaticMenuHandler {

    companion object {
        const val ID = "otm"
    }

    override val id: String
        get() = ID

    override val menu: Menu
        get() = Menu(
            "test menu, nya",

            matrix {
                row(
                    menuService.makeGenericNextMenuButton("Back", StartMenu.ID)
                )
            }
        )
}