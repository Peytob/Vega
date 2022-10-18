package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import ru.vega.telegram.menu.processor.Menu

interface MenuService {

    suspend fun showMenu(chatId: ChatId, menu: Menu)

    suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu)
}
