package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService

@Component
class DisciplineDetailsSelectMenuFactory(
    private val disciplinesService: DisciplinesService,
    private val disciplineDetailsMenuFactory: DisciplineDetailsMenuFactory
) : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            val disciplines = disciplinesService.getAll()

            makeDisciplinesButtonsMatrix(disciplines) { disciplineDto, session ->
                val detailsMenu = disciplineDetailsMenuFactory.create(disciplineDto.id)
                session.menuHistory.pushNextMenu(detailsMenu)
            }.forEach(::add)

            row(makeReturnButton())
        }

        val message = "Выбери интересующую тебя дисциплину"

        return Menu(buttons, message)
    }
}