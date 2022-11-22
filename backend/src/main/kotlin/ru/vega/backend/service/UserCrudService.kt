package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.User
import ru.vega.model.dto.user.CreateTelegramUserDto
import java.util.*

interface UserCrudService {

    fun getTelegramUserByTelegramId(telegramId: Long): User?
    fun createTelegramUser(createTelegramUserDto: CreateTelegramUserDto): User
    fun getPage(usernameFilter: String, pageable: Pageable): Page<User>
    fun getById(id: UUID): User?
}