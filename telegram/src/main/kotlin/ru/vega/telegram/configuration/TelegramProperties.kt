package ru.vega.telegram.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "telegram.bot")
@ConstructorBinding
data class TelegramProperties(
    val token: String,
    val receiverType: String
)
