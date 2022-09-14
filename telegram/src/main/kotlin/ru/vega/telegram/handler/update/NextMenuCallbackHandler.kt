package ru.vega.telegram.handler.update

import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.extensions.utils.asCallbackQueryUpdate
import dev.inmo.tgbotapi.extensions.utils.asMessageCallbackQuery
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.data
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.MenuManager
import ru.vega.telegram.service.MenuService

@Component
class NextMenuCallbackHandler(
    private val menuService: MenuService,
    private val menuManager: MenuManager,
    private val objectMapper: ObjectMapper
) : UpdateHandler {

    override suspend fun handle(update: Update) {
        val query = update
            .asCallbackQueryUpdate()
            ?.data
            ?.asMessageCallbackQuery() ?: return

        val queryText = query.data ?: return

        val decodeNextMenuMessage = menuService.decodeNextMenuMessage(queryText)
        val menuHandler = menuManager.getMenuHandler(decodeNextMenuMessage.nextMenuId) ?: return // TODO 404 menu
        val menu = menuHandler.handle(query, objectMapper.nullNode())
        menuService.replaceMenu(query.message.chat.id, query.message.messageId, menu)
    }
}