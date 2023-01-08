package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.SpecialityService
import ru.vega.telegram.service.UniversitySpecialityService
import java.util.*

@Component
@EnableConfigurationProperties(MenuProperties::class)
class UniversitySpecialitiesMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val universitySpecialityMenuFactory: UniversitySpecialityMenuFactory,
    private val specialityService: SpecialityService,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int, universityId: UUID): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)

        val specialities = universitySpecialityService.getUniversitySpecialities(universityId, pageable)

        val buttons = matrix<Button> {

            specialities.content
                .map(::makeSpecialityButton)
                .forEach { row(it) }

            val navigation = makePagesNavigationRow(specialities) { nextPage, session ->
                session.menuHistory.changeCurrentMenu(create(nextPage, universityId))
            }
            add(navigation)

            row(makeReturnButton())
        }

        val message =
            if (specialities.empty)
                "В данном университете специальности еще не заполнены :С"
            else
                "Какая специальности тебе интересна?"

        return Menu(buttons, message)
    }

    private fun makeSpecialityButton(universitySpeciality: UniversitySpecialityDto): Button {
        val speciality = specialityService.getById(universitySpeciality.speciality) ?:
            throw EntityNotFound("Speciality with id ${universitySpeciality.speciality} not found")

        return Button(speciality.title, uuidAsByteString(universitySpeciality.id)) { session ->
            val nextMenu = universitySpecialityMenuFactory.create(universitySpeciality.id, session.user.id)
            session.menuHistory.pushNextMenu(nextMenu)
        }
    }
}
