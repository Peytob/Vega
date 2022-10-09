package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.extensions.utils.asCallbackQueryUpdate
import dev.inmo.tgbotapi.extensions.utils.asMessageCallbackQuery
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.service.NotifyService
import ru.vega.telegram.service.SessionService
import ru.vega.telegram.service.TutorService

@Component
class NotifyTutorUpdateHandler(
    private val sessionService: SessionService,
    private val tutorService: TutorService,
    private val tutorNotifyService: NotifyService
) : UpdateHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(NotifyTutorUpdateHandler::class.java)
    }

    override suspend fun handle(update: Update) {
        val query = update
            .asCallbackQueryUpdate()
            ?.data
            ?.asMessageCallbackQuery() ?: return

        val session = sessionService.getOrStartSession(query.user.id)

        session.tutorsToSendMessage.forEach {
            try {
                val tutor = tutorService.getTutorById(it) ?: run {
                    logger.error("Tutor with id {} not found!", it)
                    return
                }

                val chatId = ChatId(tutor.notificationChatId)
                tutorNotifyService.newStudentNotify(chatId, query.from)
            } catch (exception: Exception) {
                logger.error("Error while sending notification about new student!", exception)
            }
        }

        session.tutorsToSendMessage.clear()
    }

    override fun getOrder() = Int.MAX_VALUE
}