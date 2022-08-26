package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.extensions.utils.asCommonMessage
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.abstracts.Message

abstract class CommonMessageHandler : MessageHandler() {

    override suspend fun handleMessage(message: Message) {
        val commonMessage = message.asCommonMessage() ?: return
        if (!filter(commonMessage)) {
            return
        }

        handleMessage(commonMessage)
    }

    abstract suspend fun handleMessage(message: CommonMessage<*>): Boolean
}