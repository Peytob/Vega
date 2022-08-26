package ru.vega.telegram.service

import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.vega.telegram.command.Command
import javax.annotation.PostConstruct

@Service
class CommandServiceImpl(
    commands: Collection<Command>
) : CommandService {

    companion object {
        private val logger = LoggerFactory.getLogger(CommandServiceImpl::class.java)
    }

    private val commands = commands.associateBy { it.getCommandString() }

    @PostConstruct
    private fun logCommandsList() {
        logger.info("Commands service initialized with {} commands.", commands.size)
        val handlersList = commands.map(Map.Entry<String, Command>::key).toList()
        logger.info("Commands list: {}", handlersList)
    }

    override fun executeCommand(command: String, message: CommonMessage<*>) {
        logger.debug("Executing command {}", command)
        val executableCommand = commands[command]

        if (executableCommand == null) {
            logger.error("Command {} not found", command)
        } else {
            executableCommand.execute(message)
        }
    }
}
