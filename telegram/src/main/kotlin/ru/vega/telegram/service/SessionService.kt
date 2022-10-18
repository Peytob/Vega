package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.message.abstracts.Message
import ru.vega.telegram.model.entity.Session

interface SessionService {

    fun getSession(message: Message): Session?

    fun startSession(message: Message): Session

    fun isSessionActive(message: Message): Boolean

    fun refreshSession(message: Message)
}