package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.enums.EducationForm

@Component
class SpecialitiesEducationFormSelectMenuFactory(
    private val specialityScoreSelectMenuFactory: SpecialityScoreSelectMenuFactory
) : MenuFactory {

    fun create(): Menu {

        val buttons = matrix<Button> {

            val budgetForm = Button("Только на бюджетную основу", "budget") { session ->
                session.speciality.educationForm = setOf(EducationForm.BUDGET)
                session.menuHistory.pushNextMenu(specialityScoreSelectMenuFactory.create())
            }

            row(budgetForm)

            val contractForm = Button("На бюджетную или коммерческую основу", "contract") { session ->
                session.speciality.educationForm = setOf(EducationForm.BUDGET, EducationForm.CONTRACT)
                session.menuHistory.pushNextMenu(specialityScoreSelectMenuFactory.create())
            }

            row(contractForm)

            row(makeReturnButton())
        }

        val message = "Как бы ты хотел поступать в университет?"

        return Menu(buttons, message)
    }
}
