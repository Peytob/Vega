package ru.vega.backend.service

import ru.vega.backend.entity.TelegramUserEntity

interface TelegramUserService {

    fun getTelegramUserByTelegramId(telegramId: Long): TelegramUserEntity?
}