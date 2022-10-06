package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import ru.vega.telegram.model.menu.Menu

class TutorDetailsMenu : MenuHandler {

    companion object {
        const val ID = "tdm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        TODO("Not yet implemented")
    }
}
