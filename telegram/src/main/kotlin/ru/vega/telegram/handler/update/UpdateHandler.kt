package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.types.update.abstracts.Update

interface UpdateHandler {

    suspend fun handle(update: Update)

    fun getOrder(): Int = 1
}
