package ru.vega.telegram.configuration

import dev.inmo.tgbotapi.bot.Ktor.telegramBot
import dev.inmo.tgbotapi.bot.RequestsExecutor
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(TelegramProperties::class)
class TelegramConfiguration {

    @Bean
    fun requestsExecutor(telegramProperties: TelegramProperties): RequestsExecutor = telegramBot(telegramProperties.token)

}
