package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.enums.TutorMeetingForm

@Component
class TutorMeetingTypeSelectMenuFactory(
    private val tutorSelectDisciplineMenuFactory: TutorSelectDisciplineMenuFactory
) : MenuFactory {

    fun create(): Menu {

        val buttons = matrix<Button> {
            val online = Button("Только онлайн", "online") { session ->
                session.tutor.meetingForm = TutorMeetingForm.ONLINE
                val nextMenu = tutorSelectDisciplineMenuFactory.create()
                session.menuHistory.pushNextMenu(nextMenu)
            }
            row(online)

            val offline = Button("Только очно", "offline") { session ->
                session.tutor.meetingForm = TutorMeetingForm.OFFLINE
                val nextMenu = tutorSelectDisciplineMenuFactory.create()
                session.menuHistory.pushNextMenu(nextMenu)
            }
            row(offline)

            row(makeReturnButton())
        }

        val message = "Как бы ты хотел встречаться со своим преподавателем?"

        return Menu(buttons, message)
    }
}
