package ru.vega.telegram.service

import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import org.springframework.stereotype.Service
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.NextMenuData

@Service
class MenuServiceImpl(
    private val messageService: MessageService,
    private val objectMapper: ObjectMapper
) : MenuService {

    override suspend fun showMenu(chatId: ChatId, menu: Menu) {
        messageService.sendMessage(
            chatId = chatId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(menu.replyMarkup)
        )
    }

    override suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu) {
        messageService.editMessage(
            chatId = chatId,
            messageId = messageId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(menu.replyMarkup)
        )
    }

    override fun encodeNextMenuMessage(nextMenuData: NextMenuData): String =
        "${nextMenuData.nextMenuId};${nextMenuData.data}"

    override fun decodeNextMenuMessage(menuDataString: String): NextMenuData {
        val (menuId, jsonDataString) = menuDataString
            .split(";", limit = 2)
            .map(String::trim)

        return NextMenuData(
            menuId,
            objectMapper.readTree(jsonDataString)
        )
    }
}