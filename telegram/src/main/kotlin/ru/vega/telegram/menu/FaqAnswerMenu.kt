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
import ru.vega.telegram.model.menu.PageSelectArguments
import ru.vega.telegram.service.FaqService
import ru.vega.telegram.service.MenuService

@Component
class FaqAnswerMenu(
    private val faqService: FaqService,
    private val objectMapper: ObjectMapper,
    private val menuService: MenuService
) : MenuHandler {

    companion object {
        const val ID = "fam"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val (id, pageNumber) = objectMapper.treeToValue<FaqAnswerArgument>(callback)

        val question = faqService.get(id) ?: throw EntityNotFound("Question with external id $id not found")

        return Menu(
            "*${question.quest}*\n${question.answer}",

            matrix {
                row(menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    FaqSelectMenu.ID,
                    PageSelectArguments(pageNumber)))
            }
        )
    }
}
