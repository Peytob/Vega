package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name= ["start-menu"], havingValue = "MIDDLE")
class MiddleStartMenuFactory(
//    private val d
) : StartMenuFactory {

    override fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines9") {

                },
                Button("Специальности", "speciality9") {

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