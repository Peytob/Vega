package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.QuestionService
import java.util.UUID

@Component
class QuestionDetailsMenuFactory(
    private val questionService: QuestionService
) : MenuFactory {

    fun create(questionId: UUID): Menu {
        val question = (questionService.getById(questionId)
            ?: throw EntityNotFound("Question entity with id $questionId not found"))

        val buttons = matrix<Button> {
            row(makeReturnButton())
        }

        val message =
            """
                *${question.quest}*
                ${question.answer}
            """.trimIndent()

        return Menu(buttons, message)
    }
}