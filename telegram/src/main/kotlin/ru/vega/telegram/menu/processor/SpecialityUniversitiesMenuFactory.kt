package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.speciality.SpecialityDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.UniversityService
import ru.vega.telegram.service.UniversitySpecialityService

@Component
class SpecialityUniversitiesMenuFactory(
    private val specialityService: UniversitySpecialityService,
    private val universityService: UniversityService,
    private val middleSpecialityDetailsMenuFactory: MiddleSpecialityDetailsMenuFactory,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int, speciality: SpecialityDto): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val specialities = specialityService.getMiddleSpecialitiesBySpeciality(speciality.id, pageable)

        val buttons = matrix<Button> {

            specialities
                .content
                .map {
                    val university = universityService.getById(it.university) ?:
                        throw EntityNotFound("University with id ${it.university} not found")

                    Button(university.title, uuidAsByteString(university.id)) { session ->
                        val menu = middleSpecialityDetailsMenuFactory.create(it.id, session.user.id)
                        session.menuHistory.pushNextMenu(menu)
                    }
                }
                .forEach(::row)

            add(makePagesNavigationRow(specialities) { page, session ->
                session.menuHistory.changeCurrentMenu(create(page, speciality))
            })

            row(makeReturnButton())
        }

        val message = "Выбери учебное заведение, в котором ты хотел бы изучать специальность '${speciality.title}'"

        return Menu(buttons, message)
    }
}
