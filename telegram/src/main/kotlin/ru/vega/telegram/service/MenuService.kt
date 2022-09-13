package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import ru.vega.telegram.model.menu.Menu

interface MenuService {

    suspend fun showMenu(chatId: ChatId, menu: Menu)

    suspend fun replaceMenu(id: ChatId, messageId: MessageIdentifier, menu: Menu)
}
