package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.CallbackDataInlineKeyboardButton
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.SpecialitySelectMenuArgument
import ru.vega.telegram.service.DisciplinesService
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.SessionService

@Component
class SpecialityDisciplinesSelectMenu(
    private val disciplinesService: DisciplinesService,
    private val menuService: MenuService,
    private val sessionService: SessionService,
    private val objectMapper: ObjectMapper
) : MenuHandler {

    companion object {
        const val ID = "sds"
    }

    override val id: String
        get() = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val selectedDisciplinesIds = sessionService
            .getOrStartSession(message.user.id)
            .selectedDisciplinesIds

        if (!callback.isNull) {
            val argument = objectMapper.treeToValue<SpecialitySelectMenuArgument>(callback)
            switchSetElement(selectedDisciplinesIds, argument.argumentExternalId)
        }

        val menuMatrix = matrix<CallbackDataInlineKeyboardButton> {
            disciplinesService.getAll().map {
                val title = if (selectedDisciplinesIds.contains(it.externalId)) SELECTED_SYMBOL + it.title else it.title
                menuService.makeGenericNextMenuButton(title, ID, SpecialitySelectMenuArgument(it.externalId))
            }.chunked(3)
            .forEach { row(*it.toTypedArray()) }

            if (selectedDisciplinesIds.size != 0) {
                row(
                    menuService.makeGenericNextMenuButton("Дальше", "TODO")
                )
            }

            row(
                menuService.makeGenericNextMenuButton(RETURN_BUTTON_TEXT, StartMenu.ID)
            )
        }

        return Menu("Выбери дисциплины, которые ты сдавал во время проведения ЕГЭ!", menuMatrix)
    }

    private fun switchSetElement(set: MutableSet<String>, element: String) {
        if (set.contains(element)) {
            set.remove(element)
        } else {
            set.add(element)
        }
    }
}