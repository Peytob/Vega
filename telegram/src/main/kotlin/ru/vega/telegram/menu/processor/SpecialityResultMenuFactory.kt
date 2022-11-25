package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.entity.SessionSpeciality
import ru.vega.telegram.service.DisciplinesSetService
import ru.vega.telegram.service.UniversitySpecialityService

@Component
@EnableConfigurationProperties(MenuProperties::class)
class SpecialityResultMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val disciplinesSetService: DisciplinesSetService,
    private val menuProperties: MenuProperties,
    private val universitySpecialityMenuFactory: UniversitySpecialityMenuFactory
) : MenuFactory {

    fun create(page: Int, search: SessionSpeciality) : Menu {
        val universitySpecialitiesPage = getUniversitySpecialitiesPage(page, search)

        val buttons = matrix<Button> {

            universitySpecialitiesPage.content
                .map {
                    makeUniversitySelectButton(it)
                }
                .forEach(::row)

            val navigationRow = makePagesNavigationRow(universitySpecialitiesPage) { nextPage, session ->
                session.menuHistory.changeCurrentMenu(create(nextPage, session.speciality))
            }

            add(navigationRow)

            row(makeReturnButton())
        }

        val message = "Какая специальность тебе интересна?"

        return Menu(buttons, message)
    }

    private fun makeUniversitySelectButton(speciality: UniversitySpecialityDto) =
        Button("${speciality.speciality.title} (${speciality.university.shortTitle})", uuidAsByteString(speciality.id)) {
            val nextMenu = universitySpecialityMenuFactory.create(speciality.id, it.user.id)
            it.menuHistory.pushNextMenu(nextMenu)
        }

    private fun getUniversitySpecialitiesPage(page: Int, search: SessionSpeciality): Page<UniversitySpecialityDto> {
        val (disciplines, educationForm, nullableScore) = search

        val score = nullableScore ?: (disciplines.size * 100)

        val disciplinesSet = disciplinesSetService.getDisciplinesSet(disciplines)
            ?: throw EntityNotFound("No disciplines set found for disciplines: $disciplines")

        val pageable = Pageable(page, menuProperties.itemsPerPage)
        return universitySpecialityService.search(disciplinesSet, score, educationForm, pageable)
    }

}
