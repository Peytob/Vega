package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.NotifyService
import ru.vega.telegram.service.TutorService
import ru.vega.telegram.service.UserService
import java.util.*

@Component
class TutorRequestAcceptQuestionMenuFactory(
    private val notifyService: NotifyService,
    private val tutorService: TutorService,
    private val userService: UserService,
    private val tutorRequestAcceptedMenuFactory: TutorRequestAcceptedMenuFactory
) : MenuFactory {

    fun create(tutorId: UUID): Menu {

        val tutor = tutorService.getTutorById(tutorId) ?:
            throw EntityNotFound("Tutor with id $tutorId not found")

        val buttons = matrix<Button> {
            val yesButton = Button("Да, уверен!", "yes") { session ->
                // TODO Тут бы какие-то очереди привязать или что-то подобное, чтобы асинхронно их обрабатывать
                //  и быть уверенным в том, что в случае ошибки заявка сохранится
                runBlocking {
                    if (userService.createUserRequest(session.user.id.chatId, tutor.id)) {
                        notifyService.newStudentNotify(tutor, session.user)
                    }
                }

                val nextMenu = tutorRequestAcceptedMenuFactory.create()
                session.menuHistory.changeCurrentMenu(nextMenu)
            }
            row(yesButton)

            val noButton = Button("Нет, не уверен!", "no") { session ->
                session.menuHistory.moveBack()
            }
            row(noButton)
        }

        val message = "Ты уверен, что хотел бы подать заявку этому репетитору?"

        return Menu(buttons, message)
    }
}
