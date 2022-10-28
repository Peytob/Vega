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

    fun create(): Menu {
        val buttons = matrix<Button> {
            val disciplines = disciplinesService.getAll()

            makeDisciplinesButtonsMatrix(disciplines) { disciplineDto, session ->
                println("Selected ${disciplineDto.title} discipline for details...")
            }.forEach(::add)

            val returnButton = Button("Назад", "return") {
                it.menuHistory.moveBack()
            }

            row(returnButton)
        }

        val message = "Выбери"

        return Menu(buttons, message)
    }
}