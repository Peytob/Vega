package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class DisciplineDetailsSelectMenuFactory(
    private val disciplineDetailsMenuFactory: DisciplineDetailsMenuFactory
) : MenuFactory {

    fun create(disciplines: Collection<DisciplineDto>): Menu {
        val buttons = matrix<Button> {
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