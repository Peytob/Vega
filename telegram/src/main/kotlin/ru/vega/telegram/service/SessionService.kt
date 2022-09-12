package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.UserId
import ru.vega.telegram.model.entity.Session

interface SessionService {

    fun getOrStartSession(userId: UserId): Session

    fun isSessionActive(userId: UserId): Boolean
}