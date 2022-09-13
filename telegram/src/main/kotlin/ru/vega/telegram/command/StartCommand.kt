package ru.vega.telegram.command

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import ru.vega.telegram.service.MessageService

@Component
class StartCommand(
    private val messageService: MessageService
) : Command {

    companion object {
        private val logger = LogManager.getLogger()
    }

    override suspend fun execute(message: CommonMessage<*>) {
        logger.info("Start command from {}", message.from?.username)
        messageService.sendMessage(message.chat.id, "Hello world", null)
    }

    override fun getCommandString() = "/start"
}