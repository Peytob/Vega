package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.speciality.SpecialityDto
import ru.vega.model.dto.university.MiddleSpecialityDto
import ru.vega.model.dto.university.UniversityDto
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.SpecialityService
import ru.vega.telegram.service.UniversityService
import ru.vega.telegram.service.UniversitySpecialityService
import ru.vega.telegram.service.UserService
import java.util.*

@Component
class MiddleSpecialityDetailsMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val userService: UserService,
    private val universityService: UniversityService,
    private val specialityService: SpecialityService,
    private val justMessageMenuFactory: JustMessageMenuFactory,
//    private val middleUniversityDetailsMenuFactory: MiddleUniversityDetailsMenuFactory
) : MenuFactory {

    fun create(universitySpecialityId: UUID, userId: UserId) : Menu {
        val universitySpeciality = universitySpecialityService.getMiddleById(universitySpecialityId)
            ?: throw EntityNotFound("Middle university speciality with ID $universitySpecialityId not found")

        val university = universityService.getById(universitySpeciality.university)
            ?: throw EntityNotFound("University with ID ${universitySpeciality.university} not found")

        val speciality = specialityService.getById(universitySpeciality.speciality)
            ?: throw EntityNotFound("Speciality with ID ${universitySpeciality.speciality} not found")

        val buttons = matrix<Button> {
             row(makeUniversityButton(university))

//            if (userService.containsBookmark(userId.chatId, universitySpeciality.id)) {
//                val deleteBookmark = Button("Убрать из закладок", "deleteBookmark") {
//                    userService.deleteBookmark(userId.chatId, universitySpeciality.id)
//                    it.menuHistory.changeCurrentMenu(justMessageMenuFactory.create("Закладка удалена!"))
//                }
//                row(deleteBookmark)
//            } else {
//                val createBookmark = Button("Добавить в закладки", "createBookmark") {
//                    userService.createBookmark(userId.chatId, universitySpeciality.id)
//                    it.menuHistory.changeCurrentMenu(justMessageMenuFactory.create("Закладка создана!"))
//                }
//                row(createBookmark)
//            }

            row(makeReturnButton())
        }

        val message = makeMessage(universitySpeciality, speciality, university)

        return Menu(buttons, message)
    }

    private fun makeUniversityButton(university: UniversityDto) = Button(university.title,  uuidAsByteString(university.id)) { session ->
//        universityDetailsMenuFactory
//            ?.create(university.id)
//            ?.let {
//                session.menuHistory.pushNextMenu(it)
//            }
    }

    private fun makeMessage(universitySpeciality: MiddleSpecialityDto, speciality: SpecialityDto, university: UniversityDto): String {
        val budgetSummary = when (universitySpeciality.budgetPlaces) {
            0, null -> "*Бюджетная основа отсутствует*"
            else -> """
                Количество бюджетных мест: *${universitySpeciality.budgetPlaces}*
            """.trimIndent()
        }

        val contractSummary = when (universitySpeciality.contractPlaces) {
            0, null -> "*Договорная основа отсутствует*"
            else -> """
                Количество мест по договору: *${universitySpeciality.contractPlaces ?: "Неизвестно"}*
                Цена договора (очно, руб): *${parseContractPrice(universitySpeciality.intramuralPrice)}*
                Цена договора (заочно, руб): *${parseContractPrice(universitySpeciality.absentiaPrice)}*
                Цена договора (очно-заочно, руб): *${parseContractPrice(universitySpeciality.partTimePrice)}*
            """.trimIndent()
        }

        val middleScoreSummary = if (universitySpeciality.middleScore == null || universitySpeciality.middleScore == 0.0f)
            ""
        else
            "Средний балл: ${universitySpeciality.middleScore!! / 10.0f}"

        return """
                *Специальность ${speciality.title} в ${university.shortTitle}*.
                ${speciality.description}
                $middleScoreSummary
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
