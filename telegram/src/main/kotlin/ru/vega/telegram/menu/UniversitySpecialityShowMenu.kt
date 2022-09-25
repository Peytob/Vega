package ru.vega.telegram.menu

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.treeToValue
import dev.inmo.tgbotapi.types.CallbackQuery.MessageCallbackQuery
import dev.inmo.tgbotapi.utils.matrix
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.model.menu.Menu
import ru.vega.telegram.model.menu.PageSelectArguments
import ru.vega.telegram.model.menu.UniversityDetailsArgument
import ru.vega.telegram.service.MenuService
import ru.vega.telegram.service.UniversitySpecialityService

class UniversitySpecialityShowMenu(
    private val objectMapper: ObjectMapper,
    private val menuService: MenuService,
    private val universitySpecialityService: UniversitySpecialityService
) : MenuHandler {

    companion object {
        const val ID = "ussm"
    }

    override val id = ID

    override fun handle(message: MessageCallbackQuery, callback: JsonNode): Menu {
        val argument = objectMapper.treeToValue<UniversityDetailsArgument>(callback)
        val speciality = universitySpecialityService.getByExternalId(argument.externalId) ?:
            throw EntityNotFound("University speciality with ID ${argument.externalId} not found")

        val budgetSummary = when (speciality.budgetPlaces) {
            0 -> "Бюджетная основа отсутствует"
            else -> """
                Количество бюджетных мест: ${speciality.budgetPlaces ?: "Неизвестно"}
                Проходной балл на бюджетную основу: ${speciality.budgetPassingScore ?: "Неизвестно"}
            """.trimIndent()
        }

        val contractSummary = when (speciality.contractPlaces) {
            0 -> "Договорная основа отсутствует"
            else -> """
                Количество мест по договору: ${speciality.contractPlaces ?: "Неизвестно"}
                Проходной балл на договор: ${speciality.contractPassingScore ?: "Неизвестно"}
                Цена договора (очно, руб): ${parseContractPrice(speciality.intramuralPrice)}
                Цена договора (заочно, руб): ${parseContractPrice(speciality.absentiaPrice)}
                Цена договора (очно-заочно, руб): ${parseContractPrice(speciality.partTimePrice)}
            """.trimIndent()
        }

        val messageText =
            """
                Специальность ${speciality.speciality.title} в ${speciality.university.shortTitle}.
                ${speciality.speciality.description}
                $budgetSummary
                $contractSummary
            """.trimIndent()

        return Menu(
            messageText,

            matrix {
                menuService.makeGenericNextMenuButton(
                    RETURN_BUTTON_TEXT,
                    SpecialitiesSearchResultMenu.ID,
                    PageSelectArguments(argument.fromPageNumber))
            }
        )
    }

    private fun parseContractPrice(price: Int?) = when (price) {
        null -> "Неизвестно"
        0 -> "Отсутствует"
        else -> price
    }
}
