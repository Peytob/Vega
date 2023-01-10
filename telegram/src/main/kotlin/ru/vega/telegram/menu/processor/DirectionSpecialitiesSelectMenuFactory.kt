package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.SpecialityService
import ru.vega.telegram.service.UniversityService
import ru.vega.telegram.service.UniversitySpecialityService

@Component
class DirectionSpecialitiesSelectMenuFactory(
    private val universitySpecialityService: UniversitySpecialityService,
    private val specialityService: SpecialityService,
    private val universityService: UniversityService,
    private val specialityUniversitiesMenuFactory: SpecialityUniversitiesMenuFactory,
    private val menuProperties: MenuProperties,
) : MenuFactory {

    fun create(page: Int, direction: DirectionDto): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val specialities = universitySpecialityService.getMiddleSpecialitiesByDirection(direction, pageable)

        val buttons = matrix<Button> {
            specialities
                .content
                .map {
                    val speciality = specialityService.getById(it.speciality) ?:
                        throw EntityNotFound("Speciality with id ${it.speciality} not found!")

                    val university = universityService.getById(it.university) ?:
                        throw EntityNotFound("University with id ${it.university} not found!")

                    Button("${speciality.title} (${university.shortTitle})", uuidAsByteString(it.speciality)) { session ->
//                        val menu = specialityUniversitiesMenuFactory.create(it.id)
//                        session.menuHistory.pushNextMenu(menu)
                    }
                }
                .forEach(::row)
//
            add(makePagesNavigationRow(specialities) { page, session ->
                session.menuHistory.changeCurrentMenu(create(page, direction))
            })

            row(makeReturnButton())
        }

        val message = "Специальности направления '${direction.title}'"

        return Menu(buttons, message)
    }
}
