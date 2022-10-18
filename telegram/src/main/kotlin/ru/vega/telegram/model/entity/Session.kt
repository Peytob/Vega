package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.message.abstracts.Message
import ru.vega.telegram.menu.processor.Menu

data class Session(
    val message: Message,
    var menu: Menu? = null
)
