package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.User

data class Session(
    val messageIdentifier: MessageIdentifier,
    val user: User,
    val menuHistory: MenuHistory
)
