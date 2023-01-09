package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DirectionService

@Component
class DirectionSelectMenuFactory(
    private val directionService: DirectionService,
    private val directionSpecialitiesSelectMenuFactory: DirectionSpecialitiesSelectMenuFactory,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val directionsPage = directionService.getDirectionsPage(pageable)

        val buttons = matrix<Button> {
            directionsPage
                .content
                .map {
                    Button(it.title, uuidAsByteString(it.id)) { session ->
                        val menu = directionSpecialitiesSelectMenuFactory.create(0, it)
                        session.menuHistory.pushNextMenu(menu)
                    }
                }
                .forEach(::row)

            val navigationRow = makePagesNavigationRow(directionsPage) { nextPage, session ->
                session.menuHistory.changeCurrentMenu(create(nextPage))
            }
            add(navigationRow)

            row(makeReturnButton())
        }

        val message = "Какое направление специальностей тебя интересует?"

        return Menu(buttons, message)
    }
}
