package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.User
import dev.inmo.tgbotapi.types.link
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.vega.model.dto.tutor.TutorDto
import ru.vega.telegram.menu.processor.makeTutorName

@Service
class NotifyServiceImpl(
    private val messageService: MessageService
) : NotifyService {

    companion object {
        private val logger = LoggerFactory.getLogger(NotifyServiceImpl::class.java)
    }

    override suspend fun newStudentNotify(tutor: TutorDto, student: User) {
        logger.info("Making notification message for chat with id {} about {}", tutor.notificationChatId, student.username)

        val message =
            """
Новая заявка на репетиторство для ${makeTutorName(tutor)}!
Указанные в профиле имя и фамилия: ${student.firstName} ${student.lastName}
Имя профиля в телеграме: ${student.username?.username}
Ссылка на профиль: ${student.link}
            """.trimMargin()

        messageService.sendMessage(ChatId(tutor.notificationChatId), message)
    }
}