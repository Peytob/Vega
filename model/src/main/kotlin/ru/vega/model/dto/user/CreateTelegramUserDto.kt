package ru.vega.model.dto.user

data class CreateTelegramUserDto(
    val telegramId: Long,
    val surname: String?,
    val forename: String?,
    val username: String?
)
