package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.backend.entity.TelegramUserRequestEntity
import ru.vega.backend.entity.TutorEntity
import java.util.UUID

interface TelegramUserRequestRepository : JpaRepository<TelegramUserRequestEntity, UUID> {

    fun existsByTelegramUserAndTutor(telegramUser: TelegramUserEntity, tutor: TutorEntity): Boolean
}
