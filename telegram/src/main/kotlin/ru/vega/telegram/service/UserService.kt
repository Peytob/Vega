package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.Identifier
import dev.inmo.tgbotapi.types.User
import dev.inmo.tgbotapi.types.UserId
import ru.vega.model.dto.user.TelegramUserDto
import java.util.*

interface UserService {

    fun createUser(user: User)
    fun getUser(telegramId: Identifier): TelegramUserDto?
    fun createUserRequest(userId: Identifier, tutorId: UUID): Boolean
    fun containsBookmark(userId: Identifier, universitySpecialityId: UUID): Boolean
    fun deleteBookmark(userId: Identifier, universitySpecialityId: UUID)
    fun createBookmark(userId: Identifier, universitySpecialityId: UUID)
    fun getBookmarks(userId: Identifier): Collection<UUID>?
}
