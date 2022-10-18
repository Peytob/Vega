package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.types.message.abstracts.Message
import org.springframework.stereotype.Component
import ru.vega.telegram.service.SessionService

@Component
class SessionRefresherMessageHandler(
    private val sessionService: SessionService
) : MessageHandler() {

    override suspend fun handleMessage(message: Message) {
        if (sessionService.isSessionActive(message)) {
            sessionService.refreshSession(message)
        }
    }

    override fun filter(message: Message): Boolean = true

    override fun getOrder(): Int = Int.MIN_VALUE
}