package ru.vega.telegram.command

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.processor.StartMenuFactory
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService
import ru.vega.telegram.service.UserService

@Component
class StartCommand(
    private val startMenuFactory: StartMenuFactory,
    private val menuService: MenuService,
    private val sessionService: SessionService,
    private val userService: UserService
) : Command {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StartCommand::class.java)
    }

    override suspend fun execute(message: CommonMessage<*>) {
        // В случае null в from, будет видно в сообщении
        logger.info("Start command from user ${message.from}")

        val user = message.from!!

        val startMenu = startMenuFactory.create()
        val menuMessage = menuService.showMenu(message.chat.id, startMenu)
        val session = sessionService.startSession(menuMessage, user)
        session.menuHistory.pushNextMenu(startMenu)

        if (!userService.isUserExists(user)) {
            userService.createUser(user)
        }
    }

    override fun getCommandString() = "/start"
}