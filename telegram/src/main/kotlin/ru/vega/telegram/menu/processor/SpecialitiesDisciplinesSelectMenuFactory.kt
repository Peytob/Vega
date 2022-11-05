package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService
import java.util.*

@Component
class SpecialitiesDisciplinesSelectMenuFactory(
    private val disciplinesService: DisciplinesService,
    private val specialitiesEducationFormSelectMenuFactory: SpecialitiesEducationFormSelectMenuFactory
) : MenuFactory {

    fun create(selectedDisciplines: Collection<UUID>): Menu {
        val disciplines = disciplinesService.getAll()

        val buttons = matrix<Button> {
            makeDisciplinesButtonsMatrix(disciplines, selectedDisciplines) { discipline, session ->
                val sessionSelectedDisciplines = session.speciality.selectedDisciplines

                if (sessionSelectedDisciplines.contains(discipline.id)) {
                    sessionSelectedDisciplines.remove(discipline.id)
                } else {
                    sessionSelectedDisciplines.add(discipline.id)
                }

                session.menuHistory.changeCurrentMenu(create(sessionSelectedDisciplines))
            }
            .forEach(::add)

            if (selectedDisciplines.isNotEmpty()) {
                val specialitySearchTypeSelect = Button("Далее...", "next") { session ->
                    val nextMenu = specialitiesEducationFormSelectMenuFactory.create()
                    session.menuHistory.pushNextMenu(nextMenu)
                }

                row(specialitySearchTypeSelect)
            }

            row(makeReturnButton())
        }

        val message = "Выбери дисциплины, которые ты сдавал во время ЕГЭ!"

        return Menu(buttons, message)
    }
}
