package ru.vega.telegram.command

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.StartMenu
import ru.vega.telegram.service.MenuService

@Component
class StartCommand(
    private val menuService: MenuService,
    private val startMenu: StartMenu
) : Command {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StartCommand::class.java)
    }

    override suspend fun execute(message: CommonMessage<*>) {
        logger.info("Start command from {}", message.from?.username)
        menuService.showMenu(message.chat.id, startMenu.menu)
    }

    override fun getCommandString() = "/start"
}