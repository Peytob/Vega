package ru.vega.telegram.receiver

import dev.inmo.tgbotapi.bot.RequestsExecutor
import dev.inmo.tgbotapi.extensions.utils.updates.retrieving.startGettingOfUpdatesByLongPolling
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.vega.telegram.handler.update.UpdateManager

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name = ["receiver-type"], havingValue = "pool")
class LongPollingReceiverInitializer(
    private val updateManager: UpdateManager
) : ReceiverInitializer {

    companion object {
        private val logger = LoggerFactory.getLogger(LongPollingReceiverInitializer::class.java)
    }

    private val scope = CoroutineScope(Dispatchers.Default)

    override fun initialize(target: RequestsExecutor) {
        logger.info("Initialization telegram bot with long polling strategy")
        target.startGettingOfUpdatesByLongPolling {
            scope.launch {
                try {
                    updateManager.handle(it)
                } catch (e: Throwable) {
                    logger.error("Unhandled message during processing update", e)
                }
            }
        }
    }
}
