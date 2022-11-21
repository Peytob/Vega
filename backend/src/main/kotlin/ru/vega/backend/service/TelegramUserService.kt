package ru.vega.backend.service

import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.model.dto.user.CreateTelegramUserDto

interface TelegramUserService {

    fun getTelegramUserByTelegramId(telegramId: Long): TelegramUserEntity?
    fun createTelegramUser(createTelegramUserDto: CreateTelegramUserDto): TelegramUserEntity
}