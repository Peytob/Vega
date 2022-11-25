package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.UniversitySpecialityService
import ru.vega.telegram.service.UserService
import java.util.*

@Component
class UniversitySpecialityMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val userService: UserService,
    private val justMessageMenuFactory: JustMessageMenuFactory
) : MenuFactory {

    // Resolution of circular reference.
    @set:Autowired
    var universityDetailsMenuFactory: UniversityDetailsMenuFactory? = null

    fun create(universitySpecialityId: UUID, userId: UserId): Menu {
        val specialityDto = universitySpecialityService.getById(universitySpecialityId)
            ?: throw EntityNotFound("University speciality with ID $universitySpecialityId not found")

        val buttons = matrix<Button> {
            row(makeUniversityButton(specialityDto.university))

            if (userService.containsBookmark(userId.chatId, specialityDto.id)) {
                val deleteBookmark = Button("Убрать из закладок", "deleteBookmark") {
                    userService.deleteBookmark(userId.chatId, specialityDto.id)
                    it.menuHistory.changeCurrentMenu(justMessageMenuFactory.create("Закладка удалена!"))
                }
                row(deleteBookmark)
            } else {
                val createBookmark = Button("Добавить в закладки", "createBookmark") {
                    userService.createBookmark(userId.chatId, specialityDto.id)
                    it.menuHistory.changeCurrentMenu(justMessageMenuFactory.create("Закладка создана!"))
                }
                row(createBookmark)
            }

            row(makeReturnButton())
        }

        val message = makeMessage(specialityDto)

        return Menu(buttons, message)
    }

    private fun makeUniversityButton(university: UniversityDto) = Button(university.title,  uuidAsByteString(university.id)) { session ->
        universityDetailsMenuFactory
            ?.create(university.id)
            ?.let {
                session.menuHistory.pushNextMenu(it)
            }
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
