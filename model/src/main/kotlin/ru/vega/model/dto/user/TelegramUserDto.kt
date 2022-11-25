package ru.vega.model.dto.user

import java.util.UUID

data class TelegramUserDto(
    val id: UUID,
    val telegramId: Long,
    val surname: String?,
    val forename: String?,
    val username: String?,
    val bookmarksSpecialities: Collection<UUID>
)
