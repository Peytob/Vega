package ru.vega.telegram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class TelegramVegaBot

fun main(args: Array<String>) {
    runApplication<TelegramVegaBot>(*args)
}
