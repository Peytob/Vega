package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.Identifier
import dev.inmo.tgbotapi.types.User
import ru.vega.model.dto.user.TelegramUserDto

interface UserService {

    fun createUser(user: User)
    fun getUser(telegramId: Identifier): TelegramUserDto?
}
