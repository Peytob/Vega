package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.extensions.utils.withContent
import dev.inmo.tgbotapi.types.MessageEntity.textsources.BotCommandTextSource
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.content.TextContent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.service.CommandService

@Component
class CommandMessageHandler(
    private val commandService: CommandService
) : CommonMessageHandler() {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CommandMessageHandler::class.java)
    }

    override suspend fun handleMessage(message: CommonMessage<*>): Boolean {
        logger.debug("Handling common message update with message id {}", message.messageId)
        val textMessage = message.withContent<TextContent>() ?: return false
        val command = textMessage.content.text.trim()
        commandService.executeCommand(command, message)
        return true
    }

    override fun filter(message: Message): Boolean {
        if (message !is CommonMessage<*>) {
            return false
        }

        val content = message.content as? TextContent ?: return false

        return !content.textSources.none { it is BotCommandTextSource }
    }
}