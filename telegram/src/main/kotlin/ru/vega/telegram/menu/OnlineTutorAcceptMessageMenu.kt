package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.OnlineTutorDetailsMenuArgument
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService

@Component
class OnlineTutorAcceptMessageMenu(
    private val objectMapper: ObjectMapper,
    private val sessionService: SessionService,
    private val menuService: MenuService
) : MenuHandler {

    companion object {
        const val ID = "otcam"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val argument = objectMapper.treeToValue<OnlineTutorDetailsMenuArgument>(callback)

        val session = sessionService
            .getOrStartSession(message.user.id)

        val messageText = if (session.selectedTutors.contains(argument.tutorExternalId)) {
            "Ты уже отправлял заявку этому репетитору!"
        } else {
            session.selectedTutors.add(argument.tutorExternalId)
            session.tutorsToSendMessage.add(argument.tutorExternalId)
            "Твоя заявка отправлена репетитору!"
        }

        return Menu(
            messageText,

            matrix {
                row(menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    OnlineTutorDetailsMenu.ID,
                    callback
                ))
            }
        )
    }
}
