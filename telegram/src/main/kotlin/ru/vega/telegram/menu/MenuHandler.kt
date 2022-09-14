package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import ru.vega.telegram.model.menu.Menu

interface MenuHandler {

    val id: String

    // TODO Make other callback type on top-level instead JsonNode
    fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu
}
