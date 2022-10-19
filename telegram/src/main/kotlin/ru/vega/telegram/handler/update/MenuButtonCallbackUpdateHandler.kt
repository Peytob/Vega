package ru.vega.telegram.handler.update

import com.fasterxml.jackson.databind.ObjectMapper
import dev.inmo.tgbotapi.extensions.utils.asCallbackQueryUpdate
import dev.inmo.tgbotapi.extensions.utils.asMessageCallbackQuery
import dev.inmo.tgbotapi.extensions.utils.extensions.raw.data
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.Matrix
import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.menu.ButtonId
import ru.vega.telegram.menu.processor.StartMenuFactory
import ru.vega.telegram.model.entity.Session
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService

@Component
class MenuButtonCallbackUpdateHandler(
    private val menuService: MenuService,
    private val sessionService: SessionService,
    private val startMenuFactory: StartMenuFactory
) : UpdateHandler {

    companion object {
        private val logger = LoggerFactory.getLogger(MenuButtonCallbackUpdateHandler::class.java)
    }

    override suspend fun handle(update: Update) {
        val query = update
            .asCallbackQueryUpdate()
            ?.data
            ?.asMessageCallbackQuery() ?: return

        // TODO Обработка ошибок

        val buttonId = query.data ?: run {
            logger.error("In query update {} button id not found", query)
            return
        }

        // TODO Пренести в другой хендлер обновления сессий?

        val session = sessionService.getSession(query.message) ?:
            handleOutOfSession(query)

        logger.debug("Pressed button with id {} by user {}", buttonId, session.user)

        val menu = session.menuHistory.currentMenu ?: run {
            logger.error("Session for message ${session.messageIdentifier} from ${session.user} dont have current menu")
            return
        }

        val button = findButton(menu.buttons, buttonId) ?: run {
            logger.error("In query menu {} button with id {} not found", query, buttonId)
            return
        }

        button.callback(session)

        val menuAfterCallback = session.menuHistory.currentMenu

        if (menuAfterCallback != null) {
            menuService.replaceMenu(query.message.chat.id, query.message.messageId, menuAfterCallback)
        }
    }

    private suspend fun handleOutOfSession(query: MessageCallbackQuery): Session {
        logger.info("Message out of session. Recreating session and return session state to start menu.")

        val startMenu = startMenuFactory.create()
        val menuMessage = menuService.replaceMenu(query.message.chat.id, query.message.messageId, startMenu)
        val session = sessionService.startSession(menuMessage)
        session.menuHistory.pushNextMenu(startMenu)
        return session
    }

    private fun findButton(keyboard: Matrix<Button>, buttonId: ButtonId): Button? =
        keyboard
            .flatten()
            .firstOrNull { it.id == buttonId }
}
