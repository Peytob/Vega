package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.types.message.abstracts.Message
import org.springframework.stereotype.Component
import ru.vega.telegram.service.SessionService

@Component
class SessionRefresherMessageHandler(
    private val sessionService: SessionService
) : MessageHandler() {

    override suspend fun handleMessage(message: Message) {
        sessionService.getOrStartSession(message.chat.id)
    }

    override fun filter(message: Message): Boolean = true
}