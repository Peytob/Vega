package ru.vega.telegram.initializer

import dev.inmo.tgbotapi.bot.RequestsExecutor
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import ru.vega.telegram.receiver.ReceiverInitializer

@Component
class StartBotAfterContextStarted(
    private val receiverInitializer: ReceiverInitializer,
    private val requestsExecutor: RequestsExecutor
) : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        receiverInitializer.initialize(requestsExecutor)
    }
}