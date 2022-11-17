package ru.vega.model.dto.user

import java.util.UUID

data class TelegramUserDto(
    val id: UUID,
    val telegramId: Long,
    val firstName: String,
    val lastName: String,
    val username: String
)
