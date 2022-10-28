package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.InlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.Matrix
import dev.inmo.tgbotapi.types.message.abstracts.ContentMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import org.springframework.stereotype.Service
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Service
class MenuServiceImpl(
    private val messageService: MessageService
) : MenuService {

    override suspend fun showMenu(chatId: ChatId, menu: Menu): ContentMessage<TextContent> {
        return messageService.sendMessage(
            chatId = chatId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(makeKeyboard(menu.buttons)),
            disableWebPagePreview = menu.disablePagePreview
        )
    }

    override suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu): ContentMessage<TextContent> {
        return messageService.editMessage(
            chatId = chatId,
            messageId = messageId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(makeKeyboard(menu.buttons)),
            disableWebPagePreview = menu.disablePagePreview
        )
    }

    private fun makeKeyboard(buttons: Matrix<Button>): Matrix<InlineKeyboardButton> =
        buttons
        .map { row ->
            row.map { CallbackDataInlineKeyboardButton(it.text, it.id) }
        }
}