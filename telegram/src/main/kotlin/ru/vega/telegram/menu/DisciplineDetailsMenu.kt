package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.URLInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.model.menu.DisciplineDetailsMenuArguments
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.TutorTownSelectArgument
import ru.vega.telegram.service.DisciplinesService
import ru.vega.telegram.service.MenuService

@Component
class DisciplineDetailsMenu(
    private val disciplinesService: DisciplinesService,
    private val menuService: MenuService,
    private val objectMapper: ObjectMapper
) : MenuHandler {

    companion object {
        const val ID = "dd"
    }

    override val id: String
        get() = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val menuId = objectMapper.treeToValue<DisciplineDetailsMenuArguments>(callback)

        val discipline = disciplinesService.getByExternalId(menuId.disciplineExternalId) ?:
            throw EntityNotFound("Discipline with id ${menuId.disciplineExternalId} not found!")

        return Menu(
            """
                |Предмет: **${discipline.title}**
                |${discipline.description}
                |Экзамен состоит из ${discipline.questionsNumber} вопросов разного уровня сложности. В ${discipline.descriptionYear} году минимальный проходной балл для успешной сдачи экзамена - ${discipline.passingScore}, при среднем балле, равном ${discipline.lastMiddleScore}.
                |Прочитать подробности (демоверсии, спецификации и кодификаторы) можно на официальном сайте [ФИПИ](${discipline.fipiLink}).
                |На сайте [Сдам ГИА](${discipline.sdamGiaLink}) можно ознакомиться с историей проходных баллов, узнать расписание ЕГЭ, ознакомиться с официальной шкалой первичных и тестовых баллов.
            """.trimMargin(),

            matrix {
                row(
                    URLInlineKeyboardButton("СдамГИА", discipline.sdamGiaLink)
                )
                row(
                    URLInlineKeyboardButton("ФИПИ", discipline.fipiLink)
                )
                row(
                    menuService.makeGenericNextMenuButton("Найти репетитора", TutorTownSelectMenu.ID, TutorTownSelectArgument(0, discipline.externalId))
                )
                row(
                    menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, DisciplinesDetailsSelectMenu.ID)
                )
            }
        )
    }
}