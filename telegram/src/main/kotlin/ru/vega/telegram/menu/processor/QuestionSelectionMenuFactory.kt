package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.QuestionService

@Component
@EnableConfigurationProperties(MenuProperties::class)
class QuestionSelectionMenuFactory(
    private val questionService: QuestionService,
    private val menuProperties: MenuProperties,
    private val questionDetailsMenuFactory: QuestionDetailsMenuFactory
) : MenuFactory {

    fun create(page: Int): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val questionPage = questionService.get(pageable)

        val buttons = matrix<Button> {

            questionPage.content
                .map {
                    Button(it.quest, uuidAsByteString(it.id)) { session ->
                        session.menuHistory.pushNextMenu(questionDetailsMenuFactory.create(it.id))
                    }
                }
                .forEach { row(it) }

            row(makeReturnButton())
        }

        val message = "Какой вопрос тебя интересует?"

        return Menu(buttons, message)
    }
}
