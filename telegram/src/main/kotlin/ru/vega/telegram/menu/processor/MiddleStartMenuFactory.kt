package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name= ["start-menu"], havingValue = "MIDDLE")
class MiddleStartMenuFactory(
    private val disciplinesService: DisciplinesService,
    private val disciplineDetailsSelectMenuFactory: DisciplineDetailsSelectMenuFactory,
    private val directionSelectMenuFactory: DirectionSelectMenuFactory
) : StartMenuFactory {

    override fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines9") { session ->
                    val disciplines = disciplinesService.getMiddle()
                    session.menuHistory.pushNextMenu(disciplineDetailsSelectMenuFactory.create(disciplines))
                },
                Button("Специальности", "speciality9") { session ->
                    val menu = directionSelectMenuFactory.create(0)
                    session.menuHistory.pushNextMenu(menu)
                }
            )
        }

        val message =
            """
                Приветствую! Меня зовут Vega 💫
                Я хочу помочь тебе в выборе твоей будущей специальности и ВУЗа! Что ты хочешь узнать?
            """.trimIndent()

        return Menu(buttons, message)
    }
}