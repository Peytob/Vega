package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.TutorAcceptMessageMenuArgument
import ru.vega.telegram.model.menu.TutorDetailsMenuArgument
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService

@Component
class TutorAcceptMessageMenu(
    private val sessionService: SessionService,
    private val menuService: MenuService,
    private val objectMapper: ObjectMapper
) : MenuHandler {

    companion object {
        const val ID = "tamm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val (tutorId) = objectMapper.treeToValue<TutorAcceptMessageMenuArgument>(callback)

        val session = sessionService
            .getOrStartSession(message.user.id)

        val messageText = if (session.selectedTutors.contains(tutorId)) {
            "Ты уже отправлял заявку этому репетитору!"
        } else {
            session.selectedTutors.add(tutorId)
            session.tutorsToSendMessage.add(tutorId)
            "Твоя заявка отправлена репетитору!"
        }

        return Menu(
            messageText,

            matrix {
                row(menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    TutorDetailsMenu.ID,
                    TutorDetailsMenuArgument(tutorId)
                ))
            }
        )
    }
}
