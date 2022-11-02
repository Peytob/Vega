package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.enums.TutorMeetingForm

@Component
class SelectTutorMeetingTypeMenuFactory : MenuFactory {

    fun create(): Menu {

        val buttons = matrix<Button> {
            val online = Button("Только онлайн", "online") { session ->
                session.tutor.meetingForms = setOf(TutorMeetingForm.ONLINE)
                TODO()
            }
            row(online)

            val offline = Button("Только очно", "offline") { session ->
                session.tutor.meetingForms = setOf(TutorMeetingForm.OFFLINE)
                TODO()
            }
            row(offline)

            row(makeReturnButton())
        }

        val message = "Как бы ты хотел встречаться со своим преподавателем?"

        return Menu(buttons, message)
    }
}
