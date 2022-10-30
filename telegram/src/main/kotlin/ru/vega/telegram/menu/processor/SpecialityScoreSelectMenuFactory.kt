package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
class SpecialityScoreSelectMenuFactory : MenuFactory {

    fun create(): Menu {

        val buttons = matrix<Button> {

            val anyScore = Button("Я не хочу вводить баллы, просто покажи специальности!", "any") { session ->
                session.speciality.score = null
                TODO()
            }

            row(anyScore)

            // TODO Properties
            (140..250 step 10)
                .map {
                    Button(it.toString(), it.toString()) { session ->
                        session.speciality.score = it
                        TODO()
                    }
                }
                .chunked(2)
                .forEach(this::add)

            row(makeReturnButton())
        }

        val message = "Какие баллы ЕГЭ ты получил, сдавая предметы?"

        return Menu(buttons, message)
    }

}
