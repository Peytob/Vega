package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.tutor.TutorDto
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.TutorService
import java.util.*

@Component
class TutorDetailsMenuFactory(
    private val tutorService: TutorService,
    private val tutorRequestAcceptQuestionMenuFactory: TutorRequestAcceptQuestionMenuFactory
) : MenuFactory {

    fun create(tutorId: UUID): Menu {

        val tutor = tutorService.getTutorById(tutorId) ?:
            throw EntityNotFound("Tutor with id $tutorId not found")

        val buttons = matrix<Button> {
            val sendRequest = Button("Отправить заявку", "request") { session ->
                val nextMenu = tutorRequestAcceptQuestionMenuFactory.create(tutor.id)
                session.menuHistory.pushNextMenu(nextMenu)
            }
            row(sendRequest)

            row(makeReturnButton())
        }

        val message = makeTutorDescriptionMessage(tutor)

        return Menu(buttons, message, false)
    }

    fun makeTutorDescriptionMessage(tutor: TutorDto): String {
        val disciplines = tutor
            .disciplines
            .joinToString(",") { it.title }

        return """
                *${makeTutorName(tutor)}*
                Преподаваемые дисциплины: $disciplines
                ${tutor.description}
                [​](${tutor.photoUrl})
            """.trimIndent()
    }
}
