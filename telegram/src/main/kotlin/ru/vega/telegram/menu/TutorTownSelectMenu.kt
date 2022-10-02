package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu

@Component
class TutorTownSelectMenu : MenuHandler {

    companion object {
        const val ID = "ttsm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        TODO("Not yet implemented")
    }
}
