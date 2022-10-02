package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.PageSelectArguments
import ru.vega.telegram.service.FaqService
import ru.vega.telegram.service.MenuService

@Component
class FaqSelectMenu(
    private val faqService: FaqService,
    private val menuService: MenuService,
    private val objectMapper: ObjectMapper,
    private val menuProperties: MenuProperties
) : MenuHandler {

    companion object {
        const val ID = "faqs"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val pageNumber = objectMapper
            .treeToValue<PageSelectArguments>(callback)
            .page

        val page = faqService
            .get(Pageable(pageNumber, menuProperties.itemsPerPage))

        return Menu(
            "Выбери вопрос, который тебя интересует",

            matrix {
                page
                    .content
                    .forEach {
                        val button = menuService.makeGenericNextMenuButton(
                            it.quest,
                            FaqAnswerMenu.ID,
                            FaqAnswerArgument(it.externalId, pageNumber)
                        )

                        row(button)
                    }

                add(
                    menuService.makePagesNavigationMenu(page, ID, ::PageSelectArguments)
                )

                row(
                    menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, StartMenu.ID))
            }
        )
    }
}
