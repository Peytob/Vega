package ru.vega.telegram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TelegramVegaBot

fun main(args: Array<String>) {
    runApplication<TelegramVegaBot>(*args)
}
