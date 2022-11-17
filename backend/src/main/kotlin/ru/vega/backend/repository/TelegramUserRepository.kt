package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vega.backend.entity.TelegramUserEntity
import java.util.UUID

interface TelegramUserRepository : JpaRepository<TelegramUserEntity, UUID> {

    fun findByTelegramId(telegramId: Long): TelegramUserEntity?
}
