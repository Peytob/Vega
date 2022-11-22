package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.model.dto.user.CreateTelegramUserDto

interface TelegramUserService {

    fun getTelegramUserByTelegramId(telegramId: Long): TelegramUserEntity?
    fun createTelegramUser(createTelegramUserDto: CreateTelegramUserDto): TelegramUserEntity
    fun getPage(usernameFilter: String, pageable: Pageable): Page<TelegramUserEntity>
}