package ru.vega.telegram.handler.update

import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.extensions.utils.asCallbackQueryUpdate
import dev.inmo.tgbotapi.extensions.utils.asMessageCallbackQuery
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.data
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.service.MenuService

@Component
class MenuButtonCallbackUpdateHandler(
    private val menuService: MenuService,
    private val objectMapper: ObjectMapper
) : UpdateHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(MenuButtonCallbackUpdateHandler::class.java)
    }

    override suspend fun handle(update: Update) {
        val query = update
            .asCallbackQueryUpdate()
            ?.data
            ?.asMessageCallbackQuery() ?: return

        val queryText = query.data ?: return

        logger.info("New button callback {}", queryText)
    }
}
