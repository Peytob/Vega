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

    override val id: String = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val proxyMenuId = EnterScoreMenuProxy.ID

        return Menu(
            "Для нахождения подходящих тебе специальностей, необходимо ввести полученный тобою (или " +
                    "предполагаемый к получению) балл ЕГЭ. Ты можешь выбрать подходящий из меню ниже, или ввести " +
                    "конкретный при помощи команды /score <балл>. Например, /score 234",

            matrix {
                row(
                    menuService.makeGenericNextMenuButton("Я не хочу вводить баллы, просто покажи специальности!",
                        SpecialitiesSearchResultMenu.ID, PageSelectArguments(0)))
                row(
                    menuService.makeGenericNextMenuButton("180", proxyMenuId, ScoreMenuProxyArgument(180)))
                row(
                    menuService.makeGenericNextMenuButton("190", proxyMenuId, ScoreMenuProxyArgument(190)))
                row(
                    menuService.makeGenericNextMenuButton("200", proxyMenuId, ScoreMenuProxyArgument(200)))
                row(
                    menuService.makeGenericNextMenuButton("210", proxyMenuId, ScoreMenuProxyArgument(210)))
                row(
                    menuService.makeGenericNextMenuButton("220", proxyMenuId, ScoreMenuProxyArgument(220)))
                row(
                    menuService.makeGenericNextMenuButton("230", proxyMenuId, ScoreMenuProxyArgument(230)))
                row(
                    menuService.makeGenericNextMenuButton("240", proxyMenuId, ScoreMenuProxyArgument(240)))
                row(
                    menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, proxyMenuId))
            }
        )
    }
}