package ru.vega.telegram.menu

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.service.MenuService

@Component
class SelectTutorMeetingType(
    private val menuService: MenuService
) : StaticMenuHandler {

    companion object {
        const val ID = "stmt"
    }

    override val id = ID

    override val menu: Menu = Menu(
        "В каком формате ты хотел бы обучаться у репетитора?",

        matrix {
            row(
                menuService.makeGenericNextMenuButton(
                    "Онлайн",
                    OnlineTutorSelectDisciplineMenu.ID),

                menuService.makeGenericNextMenuButton(
                    "Очно",
                    TutorSelectDisciplineMenu.ID)
            )

            row(
                menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    StartMenu.ID
                )
            )
        }
    )
}
