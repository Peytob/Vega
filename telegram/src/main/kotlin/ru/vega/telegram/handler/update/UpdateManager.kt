package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class UpdateManager(
    handlers: List<UpdateHandler>
) : UpdateHandler {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UpdateManager::class.java)
    }

    private val handlers = handlers
        .sortedBy(UpdateHandler::getOrder)

    @PostConstruct
    private fun logUpdateHandlersList() {
        logger.info("Update handler initialized with {} handlers.", handlers.size)
        val handlersList = handlers.map { it::class.simpleName }.toList()
        logger.info("Update handlers list: {}", handlersList)
    }

    override suspend fun handle(update: Update) {
        logger.debug("Handling update with id {}: {}", update.updateId, update)
        return handlers.forEach {
            it.handle(update)
        }
    }
}
