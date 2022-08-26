package ru.vega.telegram.receiver

import dev.inmo.tgbotapi.bot.RequestsExecutor
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.telegram.configuration.TelegramWebhookProperties

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name = ["receiver-type"], havingValue = "webhook")
@EnableConfigurationProperties(TelegramWebhookProperties::class)
class WebhookReceiverInitializer : ReceiverInitializer {

    override fun initialize(target: RequestsExecutor) {
        TODO("Not yet implemented")
    }
}