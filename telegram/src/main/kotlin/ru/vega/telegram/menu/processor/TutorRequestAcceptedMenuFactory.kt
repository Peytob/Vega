package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class TutorRequestAcceptedMenuFactory : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            row(makeReturnButton())
        }

        val message = "Твоя заявка принята и отправлена репетитору!"

        return Menu(buttons, message)
    }
}
