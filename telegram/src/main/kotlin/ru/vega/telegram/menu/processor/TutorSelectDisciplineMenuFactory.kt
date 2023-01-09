package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.enums.TutorMeetingForm
import ru.vega.telegram.service.DisciplinesService

@Component
class TutorSelectDisciplineMenuFactory(
    private val disciplinesService: DisciplinesService,
    private val tutorResultMenuFactory: TutorResultMenuFactory,
    private val tutorTownSelectMenuFactory: TutorTownSelectMenuFactory
) : MenuFactory {

    fun create(): Menu {

        val disciplines = disciplinesService.getHigher()

        val buttons = matrix<Button> {
            makeDisciplinesButtonsMatrix(disciplines) { discipline, session ->
                session.tutor.disciplineId = discipline.id

                val nextMenu = when (session.tutor.meetingForm) {
                    TutorMeetingForm.ONLINE -> tutorResultMenuFactory.create(0, session.tutor)
                    TutorMeetingForm.OFFLINE -> tutorTownSelectMenuFactory.create(0)
                    null -> throw IllegalArgumentException("Tutor meeting form should be selected")
                }

                session.menuHistory.pushNextMenu(nextMenu)
            }
            .forEach(::add)

            row(makeReturnButton())
        }

        val message = "Какую дисциплину ты хотел бы изучать с репетитором?"

        return Menu(buttons, message)
    }
}
