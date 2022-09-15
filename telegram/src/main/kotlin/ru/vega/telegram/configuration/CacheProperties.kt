package ru.vega.telegram.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties(prefix = "cache.duration")
@ConstructorBinding
data class CacheProperties(
    val session: Duration,
    val backendData: Duration
)
