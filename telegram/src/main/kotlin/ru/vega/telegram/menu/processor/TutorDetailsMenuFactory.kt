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
                val chatId = ChatId(tutor.notificationChatId)
                val nextMenu = tutorRequestAcceptQuestionMenuFactory.create(chatId)
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

        val educationFormat =
            if (tutor.online && tutor.offline) "очно и онлайн"
            else if (tutor.online) "только онлайн"
            else if (tutor.offline) "только очно"
            else "репетитор сейчас не принимает новых учеников"

        return """
                *${tutor.name}*
                Преподаваемые дисциплины: $disciplines
                Формат занятий: $educationFormat
                ${tutor.description}
                Цена академического часа: ${tutor.price}
                [​](${tutor.photoUrl})
            """.trimIndent()
    }
}
