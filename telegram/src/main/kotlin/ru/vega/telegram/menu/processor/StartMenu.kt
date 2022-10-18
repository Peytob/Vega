package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.buttons.Matrix
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.slf4j.LoggerFactory
import ru.vega.telegram.menu.Button

class StartMenu : Menu {

    companion object {
        private val logger = LoggerFactory.getLogger(StartMenu::class.java)
    }

    override val buttons: Matrix<Button> = matrix {
        row(
            Button("Предметы", "disciplines") {

            },

            Button("Специальности", "specialities") {
            }
        )

        row(
            Button("Репетиторы", "tutors") {
            }
        )

        row(
            Button("FAQ", "faq") {
            }
        )
    }

    override val message: String = "Привет!"
}