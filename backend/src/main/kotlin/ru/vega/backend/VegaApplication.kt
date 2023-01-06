package ru.vega.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VegaApplication

fun main(args: Array<String>) {
    runApplication<VegaApplication>(*args)
}
