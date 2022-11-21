package ru.vega.model.dto.user

data class CreateTelegramUserDto(
    val telegramId: Long,
    val firstName: String?,
    val lastName: String?,
    val username: String?
)
