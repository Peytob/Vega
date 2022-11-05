package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.town.TownDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.TownService

@Component
@EnableConfigurationProperties(MenuProperties::class)
class TutorTownSelectMenuFactory(
    private val townService: TownService,
    private val menuProperties: MenuProperties,
    private val tutorDistrictSelectMenuFactory: TutorDistrictSelectMenuFactory
) : MenuFactory {

    fun create(page: Int): Menu {

        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val towns = townService.getTownPage(pageable)

        val buttons = matrix<Button> {

            towns.content
                .map(::makeTownButton)
                .forEach { row(it) }

            val navigationRow = makePagesNavigationRow(towns) { nextPage, session ->
                session.menuHistory.changeCurrentMenu(create(nextPage))
            }
            add(navigationRow)

            row(makeReturnButton())
        }

        val message = "Выбери город, в котором ты ищешь репетитора"

        return Menu(buttons, message)
    }

    private fun makeTownButton(town: TownDto) = Button(town.title, uuidAsByteString(town.id)) { session ->
        val nextMenu = tutorDistrictSelectMenuFactory.create(0, town.id)
        session.menuHistory.pushNextMenu(nextMenu)
    }
}
