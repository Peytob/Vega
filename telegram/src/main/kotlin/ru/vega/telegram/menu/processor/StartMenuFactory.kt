package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class StartMenuFactory(
    private val disciplineDetailsSelectMenuFactory: DisciplineDetailsSelectMenuFactory
) : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines") { session ->
                    session.menuHistory.pushNextMenu(disciplineDetailsSelectMenuFactory.create(0))
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

        val message = "sdfsdf"

        return Menu(buttons, message)
    }
}