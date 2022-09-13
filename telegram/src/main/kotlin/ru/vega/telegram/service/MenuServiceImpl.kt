package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import org.springframework.stereotype.Service
import ru.vega.telegram.model.menu.Menu

@Service
class MenuServiceImpl(
    private val messageService: MessageService
) : MenuService {

    override suspend fun showMenu(chatId: ChatId, menu: Menu) {
        messageService.sendMessage(
            chatId = chatId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(menu.replyMarkup)
        )
    }

    override suspend fun replaceMenu(id: ChatId, messageId: MessageIdentifier, menu: Menu) {
        TODO("Not yet implemented")
    }
}