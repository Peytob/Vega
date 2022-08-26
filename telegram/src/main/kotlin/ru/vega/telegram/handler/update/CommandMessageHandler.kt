package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.extensions.utils.withContent
import dev.inmo.tgbotapi.types.MessageEntity.textsources.BotCommandTextSource
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.message.content.TextContent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Component

@Component
class CommandMessageHandler: CommonMessageHandler() {

    companion object {
        private val logger: Logger = LogManager.getLogger()
    }

    override suspend fun handleMessage(message: CommonMessage<*>): Boolean {
        val textMessage = message.withContent<TextContent>() ?: return false
        logger.info("Handled message: {}", textMessage)
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