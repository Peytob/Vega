package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class JustMessageMenuFactory : MenuFactory {

    fun create(message: String, additionalButtons: List<Button> = emptyList()): Menu {

        val buttons = matrix<Button> {
            additionalButtons.forEach(::row)
            row(makeReturnButton())
        }

        return Menu(buttons, message)
    }
}