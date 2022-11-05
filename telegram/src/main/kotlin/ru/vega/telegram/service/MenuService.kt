package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import ru.vega.telegram.model.Menu

interface MenuService {

    suspend fun showMenu(chatId: ChatId, menu: Menu): ContentMessage<TextContent>

    suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu): ContentMessage<TextContent>
}
