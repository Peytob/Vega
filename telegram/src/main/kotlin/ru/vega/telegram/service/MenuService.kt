package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import ru.vega.model.utils.Page
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.MenuCallbackCommand

interface MenuService {

    suspend fun showMenu(chatId: ChatId, menu: Menu)

    suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu)

    fun encodeNextMenuMessage(menuCallbackCommand: MenuCallbackCommand): String

    fun decodeNextMenuMessage(menuDataString: String): MenuCallbackCommand

    fun makeGenericNextMenuButton(text: String, nextMenuId: String, callbackMenuData: Any? = null): CallbackDataInlineKeyboardButton

    fun makePagesNavigationMenu(page: Page<*>, nextMenuId: String): List<CallbackDataInlineKeyboardButton>
}
