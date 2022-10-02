package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.PageSelectArguments
import ru.vega.telegram.model.menu.ScoreMenuProxyArgument
import ru.vega.telegram.service.MenuService

@Component
class EnterScoreAllowMenu(
    private val menuService: MenuService
) : MenuHandler {

    companion object {
        const val ID = "esam"
    }

    private val menu: Menu = Menu(
        "Для нахождения подходящих тебе специальностей, необходимо ввести полученный тобою (или " +
        "предполагаемый к получению) балл ЕГЭ. Ты можешь выбрать подходящий из меню ниже, или ввести " +
        "конкретный при помощи команды /score <балл>. Например, /score 234",

        matrix {
            row(
                menuService.makeGenericNextMenuButton(
                    "Я не хочу вводить баллы, просто покажи специальности!",
                    SpecialitiesSearchResultMenu.ID, PageSelectArguments(0)
                )
            )

            (140..250 step 10)
                .map {
                    menuService.makeGenericNextMenuButton(
                        it.toString(),
                        EnterScoreMenuProxy.ID,
                        ScoreMenuProxyArgument(it)
                    )
                }
                .chunked(2)
                .forEach(this::add)

            row(
                menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, SpecialityDisciplinesSelectMenu.ID)
            )
        }
    )

    override val id: String = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu = menu
}