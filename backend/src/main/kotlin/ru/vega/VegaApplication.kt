package ru.vega

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class VegaApplication

fun main(args: Array<String>) {
    runApplication<VegaApplication>(*args)
}
