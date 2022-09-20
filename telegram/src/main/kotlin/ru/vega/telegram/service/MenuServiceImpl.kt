package ru.vega.telegram.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Service
import ru.vega.model.utils.Page
import ru.vega.telegram.menu.DECREMENT_BUTTON_TEXT
import ru.vega.telegram.menu.FIRST_PAGE_DECREMENT_BUTTON_TEXT
import ru.vega.telegram.menu.LAST_PAGE_INCREMENT_BUTTON_TEXT
import ru.vega.telegram.menu.PAGE_INCREMENT_BUTTON_TEXT
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.MenuCallbackCommand
import ru.vega.telegram.model.menu.PageSelectArguments

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

    override fun encodeNextMenuMessage(menuCallbackCommand: MenuCallbackCommand): String =
        objectMapper.writeValueAsString(menuCallbackCommand)

    override fun decodeNextMenuMessage(menuDataString: String): MenuCallbackCommand =
        objectMapper.readValue(menuDataString, MenuCallbackCommand::class.java)

    override fun makeGenericNextMenuButton(text: String, nextMenuId: String, callbackMenuData: Any?): CallbackDataInlineKeyboardButton {
        val data: JsonNode? = if (callbackMenuData != null) objectMapper.valueToTree(callbackMenuData) else null
        val command = encodeNextMenuMessage(MenuCallbackCommand(nextMenuId, data))
        return CallbackDataInlineKeyboardButton(text, command)
    }

    override fun makePagesNavigationMenu(page: Page<*>, nextMenuId: String): List<CallbackDataInlineKeyboardButton> = row {
        if (page.first) {
            add(
                makeGenericNextMenuButton(FIRST_PAGE_DECREMENT_BUTTON_TEXT, nextMenuId, PageSelectArguments(0))
            )
        } else {
            add(
                makeGenericNextMenuButton(DECREMENT_BUTTON_TEXT, nextMenuId, PageSelectArguments(page.number.dec()))
            )
        }

        add(
            makeGenericNextMenuButton("${page.number.inc()} / ${page.totalPages}", nextMenuId, PageSelectArguments(page.number))
        )

        if (page.last) {
            add(
                makeGenericNextMenuButton(LAST_PAGE_INCREMENT_BUTTON_TEXT, nextMenuId, PageSelectArguments(page.totalPages.dec()))
            )
        } else {
            add(
                makeGenericNextMenuButton(PAGE_INCREMENT_BUTTON_TEXT, nextMenuId, PageSelectArguments(page.number.inc()))
            )
        }
    }
}
