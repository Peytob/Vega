package ru.vega.telegram.receiver

import dev.inmo.tgbotapi.bot.RequestsExecutor

interface ReceiverInitializer {

    fun initialize(target: RequestsExecutor)
}
