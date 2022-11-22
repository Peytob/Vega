package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.vega.backend.entity.TelegramUserEntity
import java.util.UUID

interface TelegramUserRepository : JpaRepository<TelegramUserEntity, UUID> {

    fun findByTelegramId(telegramId: Long): TelegramUserEntity?
    fun existsByTelegramIdOrUsername(telegramId: Long, username: String?): Boolean
    fun findAllByUsernameContainingIgnoreCase(usernameFilter: String, pageable: Pageable): Page<TelegramUserEntity>
}
