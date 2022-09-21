package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.ScoreMenuProxyArgument
import ru.vega.telegram.service.SessionService

@Component
class EnterScoreMenuProxy(
    private val sessionService: SessionService,
    private val objectMapper: ObjectMapper,
    private val specialitiesSearchResultMenu: SpecialitiesSearchResultMenu
) : MenuHandler {

    companion object {
        const val ID = "esmp"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val newScore = objectMapper
            .treeToValue<ScoreMenuProxyArgument>(callback)
            .score

        sessionService
            .getOrStartSession(message.user.id)
            .totalScore = newScore

        return specialitiesSearchResultMenu.handle(message, callback)
    }
}
