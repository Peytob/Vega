package ru.vega.telegram.command

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.processor.StartMenu
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService

@Component
class StartCommand(
    private val menuService: MenuService,
    private val sessionService: SessionService
) : Command {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StartCommand::class.java)
    }

    override suspend fun execute(message: CommonMessage<*>) {
        logger.info("Start command from user ${message.from}")

        val session = sessionService.startSession(message)
        session.menuHistory.pushNextMenu(StartMenu())
        menuService.showMenu(message.chat.id, session.menuHistory.currentMenu!!)
    }

    override fun getCommandString() = "/start"
}