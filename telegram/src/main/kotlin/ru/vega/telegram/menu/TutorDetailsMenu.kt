package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.TutorAcceptMessageMenuArgument
import ru.vega.telegram.model.menu.TutorDetailsMenuArgument
import ru.vega.telegram.model.menu.TutorDistrictSelectMenuArgument
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.TutorService

@Component
class TutorDetailsMenu(
    private val menuService: MenuService,
    private val tutorService: TutorService,
    private val objectMapper: ObjectMapper
) : MenuHandler {

    companion object {
        const val ID = "tdm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {

        val (tutorId) = objectMapper.treeToValue<TutorDetailsMenuArgument>(callback)

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
                Я чет думаю, что тут не стоит фигачить миллиард чатов. Мб просто маленькая денюшка за объявление и выставить контакты?
                хз как ботом создавать чаты, тк судя по доком - у них прав на такое нет
                [​](${tutor.photoUrl})
            """.trimIndent(),

            matrix {
                row(menuService.makeGenericNextMenuButton(
                    "Подать заявление репетитору",
                    TutorAcceptMessageMenu.ID,
                    TutorAcceptMessageMenuArgument(tutor.externalId)
                ))
                row(menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    TutorResultMenu.ID,
                    TutorDistrictSelectMenuArgument(0, tutor.district.externalId)))
            },

            disableWebPagePreview = false
        )
    }
}
