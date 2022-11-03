package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.User
import dev.inmo.tgbotapi.types.message.abstracts.Message
import ru.vega.telegram.model.entity.Session

interface SessionService {

    fun getSession(message: Message): Session?

    /**
     * {@param message} - Сообщение, в котором происходит текущая сессия диалога с ботом.
     * {@param initiator} - Тот, кто инициировал общение с ботом, например, отправив ему команду /start
     */
    fun startSession(message: Message, initiator: User): Session

    fun isSessionActive(message: Message): Boolean
}