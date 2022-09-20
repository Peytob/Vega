package ru.vega.telegram.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "telegram.bot.menu")
@ConstructorBinding
data class MenuProperties(
    val itemsPerPage: Int
)
