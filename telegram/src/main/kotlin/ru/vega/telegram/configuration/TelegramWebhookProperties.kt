package ru.vega.telegram.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "telegram.bot.webhook")
@ConditionalOnProperty(prefix = "telegram.bot", name = ["receiverType"], havingValue = "webhook")
@ConstructorBinding
data class TelegramWebhookProperties(
    val url: String,
    val port: Int
)
