package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.extensions.utils.asMessageUpdate
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.slf4j.LoggerFactory

abstract class MessageHandler : UpdateHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(MessageHandler::class.java)
    }

    override suspend fun handle(update: Update) {
        val message = update.asMessageUpdate()?.data ?: return

        if (!filter(message)) {
            return
        }

        logger.debug("Update with id {} is message update with message id {}", update.updateId, message.messageId)
        handleMessage(message)

        return
    }

    abstract suspend fun handleMessage(message: Message)

    abstract fun filter(message: Message): Boolean
}
