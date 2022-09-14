package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.NextMenuData

interface MenuService {

    suspend fun showMenu(chatId: ChatId, menu: Menu)

    suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu)

    fun encodeNextMenuMessage(nextMenuData: NextMenuData): String

    fun decodeNextMenuMessage(menuDataString: String): NextMenuData
}
