package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.User

interface NotifyService {

    suspend fun newStudentNotify(chatId: ChatId, student: User)
}
