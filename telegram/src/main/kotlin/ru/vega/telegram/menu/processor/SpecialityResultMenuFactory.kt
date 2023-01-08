package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.speciality.SpecialityDto
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.entity.SessionSpeciality
import ru.vega.telegram.service.DisciplinesSetService
import ru.vega.telegram.service.SpecialityService
import ru.vega.telegram.service.UniversityService
import ru.vega.telegram.service.UniversitySpecialityService
import java.util.*

@Component
@EnableConfigurationProperties(MenuProperties::class)
class SpecialityResultMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val universityService: UniversityService,
    private val specialityService: SpecialityService,
    private val disciplinesSetService: DisciplinesSetService,
    private val menuProperties: MenuProperties,
    private val universitySpecialityMenuFactory: UniversitySpecialityMenuFactory
) : MenuFactory {

    fun create(page: Int, search: SessionSpeciality) : Menu {
        val universitySpecialitiesPage = getUniversitySpecialitiesPage(page, search)

        val buttons = matrix<Button> {

            universitySpecialitiesPage.content
                .map {
                    val university = universityService.getById(it.university) ?:
                        throw EntityNotFound("University with id ${it.university} not found")
                    val speciality = specialityService.getById(it.speciality) ?:
                        throw EntityNotFound("Speciality with id ${it.speciality} not found")
                    makeUniversitySelectButton(speciality, university, it.id)
                }
                .forEach(::row)

            val navigationRow = makePagesNavigationRow(universitySpecialitiesPage) { nextPage, session ->
                session.menuHistory.changeCurrentMenu(create(nextPage, session.speciality))
            }

            add(navigationRow)

            row(makeReturnButton())
        }

        val message = if (universitySpecialitiesPage.empty)
                "По твоему запросы мы не нашли ни одной специальности :("
            else
                "Какая специальность тебе интересна?"

        return Menu(buttons, message)
    }

    private fun makeUniversitySelectButton(speciality: SpecialityDto, university: UniversityDto, universitySpecialityId: UUID) =
        Button("${speciality.title} (${university.shortTitle})", uuidAsByteString(universitySpecialityId)) {
            val nextMenu = universitySpecialityMenuFactory.create(universitySpecialityId, it.user.id)
            it.menuHistory.pushNextMenu(nextMenu)
        }

    private fun getUniversitySpecialitiesPage(page: Int, search: SessionSpeciality): Page<UniversitySpecialityDto> {
        val (disciplines, educationForm, nullableScore) = search

        val score = nullableScore ?: (disciplines.size * 100)

        val disciplinesSet = disciplinesSetService.getDisciplinesSet(disciplines)
            ?: return Page.empty()

        val pageable = Pageable(page, menuProperties.itemsPerPage)
        return universitySpecialityService.search(disciplinesSet, score, educationForm, pageable)
    }

}
