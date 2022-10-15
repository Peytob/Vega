package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.model.menu.*
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.TutorService

@Component
class OnlineTutorDetailsMenu(
    private val tutorService: TutorService,
    private val objectMapper: ObjectMapper,
    private val menuService: MenuService
) : MenuHandler {

    companion object {
        const val ID = "otdm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {

        val (tutorId, page, disciplineId) = objectMapper.treeToValue<OnlineTutorDetailsMenuArgument>(callback)

        val tutor = tutorService.getTutorById(tutorId) ?: throw EntityNotFound("Tutor with id $tutorId not found")

        val disciplines = tutor
            .disciplines
            .joinToString(",") { it.title }

        val educationFormat =
            if (tutor.online && tutor.offline) "очно и онлайн"
            else if (tutor.online) "только онлайн"
            else if (tutor.offline) "только очно"
            else "репетитор сейчас не принимает новых учеников"

        return Menu(
            """
                *${tutor.name}*
                Преподаваемые дисциплины: $disciplines
                Формат занятий: $educationFormat
                ${tutor.description}
                Цена академического часа: ${tutor.price}
                [​](${tutor.photoUrl})
            """.trimIndent(),

            matrix {
                row(menuService.makeGenericNextMenuButton(
                    "Подать заявление репетитору",
                    OnlineTutorAcceptMessageMenu.ID,
                    callback
                ))

                row(menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    OnlineTutorResultMenu.ID,
                    OnlineTutorResultMenuArgument(page, disciplineId)))
            },

            disableWebPagePreview = false
        )
    }
}
