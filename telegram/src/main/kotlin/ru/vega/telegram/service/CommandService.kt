package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage

interface CommandService {

    fun executeCommand(command: String, message: CommonMessage<*>)
}
