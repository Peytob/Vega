package ru.vega.telegram.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URI

@ConfigurationProperties(prefix = "backend")
@ConstructorBinding
data class BackendProperties(
    val url: URI
)
