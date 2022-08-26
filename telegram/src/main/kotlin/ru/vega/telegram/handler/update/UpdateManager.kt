package ru.vega.telegram.handler.update

import dev.inmo.tgbotapi.types.update.abstracts.Update
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class UpdateManager(
    handlers: List<UpdateHandler>
) : UpdateHandler {

    companion object {
        private val logger = LogManager.getLogger()
    }

    private val handlers = handlers.sortedBy { it.getOrder() }

    @PostConstruct
    private fun logUpdateHandlersList() {
        logger.info("Update handler initialized with {} handlers.", handlers.size)
        val handlersList = handlers.map { it::class.simpleName }.toList()
        logger.info("Update handlers list: {}", handlersList)
    }

    override suspend fun handle(update: Update) {
        return handlers.forEach {
            it.handle(update)
        }
    }
}
