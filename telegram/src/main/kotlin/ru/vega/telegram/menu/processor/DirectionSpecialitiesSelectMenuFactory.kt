package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.SpecialityService

@Component
class DirectionSpecialitiesSelectMenuFactory(
    private val specialityService: SpecialityService,
    private val middleSpecialityUniversitiesMenuFactory: SpecialityUniversitiesMenuFactory,
    private val menuProperties: MenuProperties,
) : MenuFactory {

    fun create(page: Int, direction: DirectionDto): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val specialities = specialityService.getByDirection(direction, pageable)

        val buttons = matrix<Button> {
            specialities
                .content
                .map { speciality ->
                    Button(speciality.title, uuidAsByteString(speciality.id)) { session ->
                        val menu = middleSpecialityUniversitiesMenuFactory.create(0, speciality)
                        session.menuHistory.pushNextMenu(menu)
                    }
                }
                .forEach(::row)

            add(makePagesNavigationRow(specialities) { page, session ->
                session.menuHistory.changeCurrentMenu(create(page, direction))
            })

            row(makeReturnButton())
        }

        val message = "Специальности направления '${direction.title}'"

        return Menu(buttons, message)
    }
}
