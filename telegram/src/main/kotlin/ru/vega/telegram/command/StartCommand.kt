package ru.vega.telegram.command

import dev.inmo.tgbotapi.extensions.utils.extensions.raw.from
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class StartCommand : Command {

    companion object {
        private val logger = LoggerFactory.getLogger(StartCommand::class.java)
    }

    override fun execute(message: CommonMessage<*>) {
        logger.info("Start command from {}", message.from?.username)
    }

    override fun getCommandString() = "/start"
}