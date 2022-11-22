package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.vega.backend.entity.User
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {

    fun findByTelegramId(telegramId: Long): User?
    fun existsByTelegramIdOrUsername(telegramId: Long, username: String?): Boolean
    fun findAllByUsernameContainingIgnoreCase(usernameFilter: String, pageable: Pageable): Page<User>
}
