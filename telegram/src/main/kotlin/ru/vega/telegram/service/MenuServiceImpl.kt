package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.InlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.buttons.Matrix
import org.springframework.stereotype.Service
import ru.vega.telegram.menu.Button
import ru.vega.telegram.menu.processor.Menu

@Service
class MenuServiceImpl(
    private val messageService: MessageService
) : MenuService {

    override suspend fun showMenu(chatId: ChatId, menu: Menu) {
        messageService.sendMessage(
            chatId = chatId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(makeKeyboard(menu.buttons))
        )
    }

    override suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu) {
        messageService.editMessage(
            chatId = chatId,
            messageId = messageId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(makeKeyboard(menu.buttons))
        )
    }

    private fun makeKeyboard(buttons: Matrix<Button>): Matrix<InlineKeyboardButton> =
        buttons
        .map { row ->
            row.map { CallbackDataInlineKeyboardButton(it.text, it.id) }
        }
}