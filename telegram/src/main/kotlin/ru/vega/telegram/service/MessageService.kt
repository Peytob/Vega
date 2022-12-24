package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.MarkdownParseMode
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent

interface MessageService {

    suspend fun sendMessage(
        chatId: ChatId,
        text: String,
        replyMarkup: KeyboardMarkup? = null,
        parseMode: ParseMode? = MarkdownParseMode,
        disableWebPagePreview: Boolean = false): ContentMessage<TextContent>

    suspend fun editMessage(
        chatId: ChatId,
        messageId: MessageIdentifier,
        text: String,
        replyMarkup: InlineKeyboardMarkup? = null,
        parseMode: ParseMode? = MarkdownParseMode,
        disableWebPagePreview: Boolean = false): ContentMessage<TextContent>
}
