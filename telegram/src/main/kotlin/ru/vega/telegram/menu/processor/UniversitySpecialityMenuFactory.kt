package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.UniversitySpecialityService
import java.util.*

@Component
class UniversitySpecialityMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService
) : MenuFactory {

    fun create(universitySpecialityId: UUID): Menu {
        val specialityDto = universitySpecialityService.getById(universitySpecialityId)
            ?: throw EntityNotFound("University speciality with ID $universitySpecialityId not found")

        val buttons = matrix<Button> {
            row(makeReturnButton())
        }

        val message = makeMessage(specialityDto)

        return Menu(buttons, message)
    }

    private fun makeMessage(speciality: UniversitySpecialityDto): String {
        val budgetSummary = when (speciality.budgetPlaces) {
            0 -> "*Бюджетная основа отсутствует*"
            else -> """
                Количество бюджетных мест: *${speciality.budgetPlaces ?: "Неизвестно"}*
                Проходной балл на бюджетную основу: *${speciality.budgetPassingScore ?: "Неизвестно"}*
            """.trimIndent()
        }

        val contractSummary = when (speciality.contractPlaces) {
            0 -> "*Договорная основа отсутствует*"
            else -> """
                Количество мест по договору: *${speciality.contractPlaces ?: "Неизвестно"}*
                Проходной балл на договор: *${speciality.contractPassingScore ?: "Неизвестно"}*
                Цена договора (очно, руб): *${parseContractPrice(speciality.intramuralPrice)}*
                Цена договора (заочно, руб): *${parseContractPrice(speciality.absentiaPrice)}*
                Цена договора (очно-заочно, руб): *${parseContractPrice(speciality.partTimePrice)}*
            """.trimIndent()
        }

        return """
                *Специальность ${speciality.speciality.title} в ${speciality.university.shortTitle}*.
                ${speciality.speciality.description}
                $budgetSummary
                $contractSummary
            """.trimIndent()
    }

    private fun parseContractPrice(price: Int?) = when (price) {
        null -> "Неизвестно"
        0 -> "Отсутствует"
        else -> price
    }
}
