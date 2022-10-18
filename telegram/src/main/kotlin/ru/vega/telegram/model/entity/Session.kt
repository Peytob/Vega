package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.message.abstracts.Message

data class Session(
    val message: Message,
    val menuHistory: MenuHistory
)
