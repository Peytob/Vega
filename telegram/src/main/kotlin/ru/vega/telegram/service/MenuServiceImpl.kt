package ru.vega.telegram.service

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.types.ChatId
import dev.inmo.tgbotapi.types.MessageIdentifier
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardMarkup
import dev.inmo.tgbotapi.types.captionField
import dev.inmo.tgbotapi.types.disableWebPagePreviewField
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Service
import ru.vega.model.utils.Page
import ru.vega.telegram.menu.DECREMENT_BUTTON_TEXT
import ru.vega.telegram.menu.FIRST_PAGE_DECREMENT_BUTTON_TEXT
import ru.vega.telegram.menu.LAST_PAGE_INCREMENT_BUTTON_TEXT
import ru.vega.telegram.menu.PAGE_INCREMENT_BUTTON_TEXT
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.MenuCallbackCommand

@Service
class MenuServiceImpl(
    private val messageService: MessageService,
    private val objectMapper: ObjectMapper
) : MenuService {

    override suspend fun showMenu(chatId: ChatId, menu: Menu) {
        messageService.sendMessage(
            chatId = chatId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(menu.replyMarkup),
            disableWebPagePreview = menu.disableWebPagePreview
        )
    }

    override suspend fun replaceMenu(chatId: ChatId, messageId: MessageIdentifier, menu: Menu) {
        messageService.editMessage(
            chatId = chatId,
            messageId = messageId,
            text = menu.message,
            replyMarkup = InlineKeyboardMarkup(menu.replyMarkup),
            disableWebPagePreview = menu.disableWebPagePreview
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

    override fun makePagesNavigationMenu(
        page: Page<*>,
        nextMenuId: String,
        pageMappingMethod: (Int) -> Any
    ): List<CallbackDataInlineKeyboardButton> = row {
        if (page.first) {
            add(
                makeGenericNextMenuButton(FIRST_PAGE_DECREMENT_BUTTON_TEXT, nextMenuId, pageMappingMethod(0))
            )
        } else {
            add(
                makeGenericNextMenuButton(DECREMENT_BUTTON_TEXT, nextMenuId, pageMappingMethod(page.number.dec()))
            )
        }

        add(
            makeGenericNextMenuButton("${page.number.inc()} / ${page.totalPages}", nextMenuId, pageMappingMethod(page.number))
        )

        if (page.last) {
            add(
                makeGenericNextMenuButton(LAST_PAGE_INCREMENT_BUTTON_TEXT, nextMenuId, pageMappingMethod(page.totalPages.dec()))
            )
        } else {
            add(
                makeGenericNextMenuButton(PAGE_INCREMENT_BUTTON_TEXT, nextMenuId, pageMappingMethod(page.number.inc()))
            )
        }
    }
}
