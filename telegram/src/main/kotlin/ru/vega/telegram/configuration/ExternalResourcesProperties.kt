package ru.vega.telegram.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConfigurationProperties(prefix = "external-links")
@ConstructorBinding
data class ExternalResourcesProperties(
    val backendUrl: URI,
    val spoBotUrl: URI,
    val telegramSuggestionChat: URI,
    val telegramVegaChat: URI,
    val vkGroup: URI,
    val serviceContact: URI,
    val vegaBot: URI
)
