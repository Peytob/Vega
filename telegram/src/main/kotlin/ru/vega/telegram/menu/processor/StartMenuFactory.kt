package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class StartMenuFactory(
    private val disciplineDetailsSelectMenuFactory: DisciplineDetailsSelectMenuFactory,
    private val questionSelectMenuFactory: QuestionSelectionMenuFactory,
    private val specialitiesDisciplinesSelectMenuFactory: SpecialitiesDisciplinesSelectMenuFactory,
    private val tutorMeetingTypeSelectMenuFactory: TutorMeetingTypeSelectMenuFactory
) : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines") { session ->
                    session.menuHistory.pushNextMenu(disciplineDetailsSelectMenuFactory.create())
                },

                Button("Специальности", "specialities") { session ->
                    session.menuHistory.pushNextMenu(specialitiesDisciplinesSelectMenuFactory.create(session.speciality.selectedDisciplines))
                }
            )

            row(
                Button("Репетиторы", "tutors") { session ->
                    session.menuHistory.pushNextMenu(tutorMeetingTypeSelectMenuFactory.create())
                }
            )

            row(
                Button("FAQ", "faq") { session ->
                    session.menuHistory.pushNextMenu(questionSelectMenuFactory.create(0))
                }
            )
        }

        val message =
            """
                Приветствую! Меня зовут Vega 💫
                Я хочу помочь тебе в выборе твоей будущей специальности и ВУЗа! Что ты хочешь узнать?
            """.trimIndent()

        return Menu(buttons, message)
    }
}