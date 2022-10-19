package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService

@Component
class DisciplineDetailsSelectMenuFactory(
    private val disciplinesService: DisciplinesService
) : MenuFactory {

    fun create(page: Int): Menu {
        if (page < 0) {
            throw IllegalArgumentException("Page can't be less than zero. Current value is $page")
        }

        val buttons = matrix<Button> {
            val disciplines = disciplinesService.getAll()

            disciplines
                .map {
                    Button(it.title, it.externalId) {
                    }
                }
                .chunked(3)
                .forEach(::add)

            val returnButton = Button("Назад", "return") {
                it.menuHistory.moveBack()
            }

            row(returnButton)
        }

        val message = "Выбери"

        return Menu(buttons, message)
    }
}