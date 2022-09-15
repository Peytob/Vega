package ru.vega.telegram.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import org.springframework.stereotype.Service
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.MenuCallbackCommand

@Service
class MenuServiceImpl(
    private val messageService: MessageService,
) : MenuService {

    private val objectMapper: ObjectMapper = ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

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

    override fun encodeNextMenuMessage(menuCallbackCommand: MenuCallbackCommand): String =
        objectMapper.writeValueAsString(menuCallbackCommand)

    override fun decodeNextMenuMessage(menuDataString: String): MenuCallbackCommand =
        objectMapper.readValue(menuDataString, MenuCallbackCommand::class.java)

    override fun makeGenericNextMenuButton(text: String, nextMenuId: String, callbackMenuData: Any?): CallbackDataInlineKeyboardButton {
        val data: JsonNode? = if (callbackMenuData != null) objectMapper.valueToTree(callbackMenuData) else null
        val command = encodeNextMenuMessage(MenuCallbackCommand(nextMenuId, data))
        return CallbackDataInlineKeyboardButton(text, command)
    }
}