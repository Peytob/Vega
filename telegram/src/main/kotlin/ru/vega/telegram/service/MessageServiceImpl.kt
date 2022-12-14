package ru.vega.telegram.service

import dev.inmo.tgbotapi.bot.RequestsExecutor
import dev.inmo.tgbotapi.extensions.api.edit.text.editMessageText
import dev.inmo.tgbotapi.extensions.api.send.sendMessage
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.ParseMode.MarkdownParseMode
import dev.inmo.tgbotapi.types.ParseMode.ParseMode
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.KeyboardMarkup
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(
    private val requestsExecutor: RequestsExecutor
) : MessageService {

    override suspend fun sendMessage(
        chatId: ChatId,
        text: String,
        replyMarkup: KeyboardMarkup?,
        parseMode: ParseMode?,
        disableWebPagePreview: Boolean
    ): ContentMessage<TextContent> =
        requestsExecutor.sendMessage(
            chatId = chatId,
            parseMode = parseMode,
            text = text,
            disableWebPagePreview = disableWebPagePreview,
            replyMarkup = replyMarkup)

    override suspend fun editMessage(
        chatId: ChatId,
        messageId: MessageIdentifier,
        text: String,
        replyMarkup: InlineKeyboardMarkup?,
        parseMode: ParseMode?,
        disableWebPagePreview: Boolean
    ): ContentMessage<TextContent> =
        requestsExecutor.editMessageText(
            chatId = chatId,
            messageId = messageId,
            parseMode = parseMode,
            text = text,
            disableWebPagePreview = disableWebPagePreview,
            replyMarkup = replyMarkup)
}