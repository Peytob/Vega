package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import ru.vega.telegram.model.menu.Menu

interface StaticMenuHandler : MenuHandler {

    val menu: Menu

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu = menu
}