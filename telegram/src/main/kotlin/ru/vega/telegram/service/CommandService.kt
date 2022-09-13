package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage

interface CommandService {

    suspend fun executeCommand(command: String, message: CommonMessage<*>)
}
