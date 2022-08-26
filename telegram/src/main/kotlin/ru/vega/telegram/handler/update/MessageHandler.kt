package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.extensions.utils.asMessageUpdate
import dev.inmo.tgbotapi.types.message.abstracts.Message
import dev.inmo.tgbotapi.types.update.abstracts.Update

abstract class MessageHandler : UpdateHandler {

    override suspend fun handle(update: Update) {
        val message = update.asMessageUpdate()?.data ?: return
        if (!filter(message)) {
            return
        }

        handleMessage(message)

        return
    }

    abstract suspend fun handleMessage(message: Message)

    abstract fun filter(message: Message): Boolean
}
