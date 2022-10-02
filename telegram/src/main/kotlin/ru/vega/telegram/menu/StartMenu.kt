package ru.vega.telegram.menu

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.PageSelectArguments
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
                    menuService.makeGenericNextMenuButton("Предметы", DisciplinesDetailsSelectMenu.ID),
                    menuService.makeGenericNextMenuButton("Специальности", SpecialityDisciplinesSelectMenu.ID),
                )
                row(
                    menuService.makeGenericNextMenuButton("Репетиторы!", TutorSelectDisciplineMenu.ID)
                )
                row(
                    menuService.makeGenericNextMenuButton("FAQ", FaqSelectMenu.ID, PageSelectArguments(0))
                )
            }
        )
}