package ru.vega.telegram.receiver

import dev.inmo.tgbotapi.bot.RequestsExecutor
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.startGettingOfUpdatesByLongPolling
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.logging.log4j.LogManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name = ["receiver-type"], havingValue = "pool")
class LongPollingReceiverInitializer() : ReceiverInitializer {

    companion object {
        private val logger = LogManager.getLogger()
    }

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun initialize(target: RequestsExecutor) {
        logger.info("Initialization telegram bot with long polling strategy")
        target.startGettingOfUpdatesByLongPolling {
            scope.launch {
                logger.info(it.data)
            }
        }
    }
}
