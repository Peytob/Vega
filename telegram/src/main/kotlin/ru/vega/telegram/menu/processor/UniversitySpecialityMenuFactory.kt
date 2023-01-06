package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.vega.model.dto.speciality.SpecialityDto
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.SpecialityService
import ru.vega.telegram.service.UniversityService
import ru.vega.telegram.service.UniversitySpecialityService
import ru.vega.telegram.service.UserService
import java.util.*

@Component
class UniversitySpecialityMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val userService: UserService,
    private val specialityService: SpecialityService,
    private val universityService: UniversityService,
    private val justMessageMenuFactory: JustMessageMenuFactory
) : MenuFactory {

    // Resolution of circular reference.
    @set:Autowired
    var universityDetailsMenuFactory: UniversityDetailsMenuFactory? = null

    fun create(universitySpecialityId: UUID, userId: UserId): Menu {
        val universitySpeciality = universitySpecialityService.getById(universitySpecialityId)
            ?: throw EntityNotFound("University speciality with ID $universitySpecialityId not found")

        val university = universityService.getById(universitySpeciality.university)
            ?: throw EntityNotFound("University with ID ${universitySpeciality.university} not found")

        val speciality = specialityService.getById(universitySpeciality.speciality)
            ?: throw EntityNotFound("Speciality with ID ${universitySpeciality.speciality} not found")

        val buttons = matrix<Button> {
            row(makeUniversityButton(university))

            if (userService.containsBookmark(userId.chatId, universitySpeciality.id)) {
                val deleteBookmark = Button("Убрать из закладок", "deleteBookmark") {
                    userService.deleteBookmark(userId.chatId, universitySpeciality.id)
                    it.menuHistory.changeCurrentMenu(justMessageMenuFactory.create("Закладка удалена!"))
                }
                row(deleteBookmark)
            } else {
                val createBookmark = Button("Добавить в закладки", "createBookmark") {
                    userService.createBookmark(userId.chatId, universitySpeciality.id)
                    it.menuHistory.changeCurrentMenu(justMessageMenuFactory.create("Закладка создана!"))
                }
                row(createBookmark)
            }

            row(makeReturnButton())
        }

        val message = makeMessage(universitySpeciality, speciality, university)

        return Menu(buttons, message)
    }

    private fun makeUniversityButton(university: UniversityDto) = Button(university.title,  uuidAsByteString(university.id)) { session ->
        universityDetailsMenuFactory
            ?.create(university.id)
            ?.let {
                session.menuHistory.pushNextMenu(it)
            }
    }

    private fun makeMessage(universitySpeciality: UniversitySpecialityDto, speciality: SpecialityDto, university: UniversityDto): String {
        val budgetSummary = when (universitySpeciality.budgetPlaces) {
            0 -> "*Бюджетная основа отсутствует*"
            else -> """
                Количество бюджетных мест: *${universitySpeciality.budgetPlaces ?: "Неизвестно"}*
                Проходной балл на бюджетную основу: *${universitySpeciality.budgetPassingScore ?: "Неизвестно"}*
            """.trimIndent()
        }

        val contractSummary = when (universitySpeciality.contractPlaces) {
            0 -> "*Договорная основа отсутствует*"
            else -> """
                Количество мест по договору: *${universitySpeciality.contractPlaces ?: "Неизвестно"}*
                Проходной балл на договор: *${universitySpeciality.contractPassingScore ?: "Неизвестно"}*
                Цена договора (очно, руб): *${parseContractPrice(universitySpeciality.intramuralPrice)}*
                Цена договора (заочно, руб): *${parseContractPrice(universitySpeciality.absentiaPrice)}*
                Цена договора (очно-заочно, руб): *${parseContractPrice(universitySpeciality.partTimePrice)}*
            """.trimIndent()
        }

        return """
                *Специальность ${speciality.title} в ${university.shortTitle}*.
                ${speciality.description}
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
