package ru.vega.backend.service

import ru.vega.backend.entity.TelegramUserEntity
import ru.vega.backend.entity.TelegramUserRequestEntity
import ru.vega.backend.entity.TutorEntity

interface TelegramUserService {
    fun createTutorRequest(student: TelegramUserEntity, tutor: TutorEntity): TelegramUserRequestEntity

}
