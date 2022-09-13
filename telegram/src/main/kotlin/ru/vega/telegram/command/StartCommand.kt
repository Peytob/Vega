package ru.vega.telegram.command

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.StartMenu
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.MessageService

@Component
class StartCommand(
    private val messageService: MessageService,
    private val menuService: MenuService
) : Command {

    companion object {
        private val logger = LogManager.getLogger()
    }

    override suspend fun execute(message: CommonMessage<*>) {
        logger.info("Start command from {}", message.from?.username)
        menuService.showMenu(message.chat.id, StartMenu.MENU)
    }

    override fun getCommandString() = "/start"
}