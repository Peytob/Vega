package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class StartMenuFactory(
    private val disciplineDetailsSelectMenuFactory: DisciplineDetailsSelectMenuFactory,
    private val questionSelectMenuFactory: QuestionSelectionMenuFactory
) : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines") { session ->
                    session.menuHistory.pushNextMenu(disciplineDetailsSelectMenuFactory.create())
                },

                Button("Специальности", "specialities") {
                }
            )

            row(
                Button("Репетиторы", "tutors") {
                }
            )

            row(
                Button("FAQ", "faq") { session ->
                    session.menuHistory.pushNextMenu(questionSelectMenuFactory.create(0))
                }
            )
        }

        val message = "sdfsdf"

        return Menu(buttons, message)
    }
}