package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService
import java.util.*

@Component
class DisciplineDetailsMenuFactory(
    private val disciplinesService: DisciplinesService
) {

    fun create(disciplineId: UUID): Menu {
        val discipline = disciplinesService.getById(disciplineId)
            ?: throw EntityNotFound("Discipline with id $disciplineId not found")

        val buttons = matrix<Button> {
            row(makeReturnButton())
        }

        val message =
            """
                |Предмет: **${discipline.title}**
                |${discipline.description}
                |Экзамен состоит из ${discipline.questionsNumber} вопросов разного уровня сложности. В ${discipline.descriptionYear} году минимальный проходной балл для успешной сдачи экзамена - ${discipline.passingScore}, при среднем балле, равном ${discipline.lastMiddleScore}.
                |Прочитать подробности (демоверсии, спецификации и кодификаторы) можно на официальном сайте [ФИПИ](${discipline.fipiLink}).
                |На сайте [Сдам ГИА](${discipline.sdamGiaLink}) можно ознакомиться с историей проходных баллов, узнать расписание ЕГЭ, ознакомиться с официальной шкалой первичных и тестовых баллов.
            """.trimMargin()

        return Menu(buttons, message)
    }
}
