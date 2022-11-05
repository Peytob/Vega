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
class UniversitiesTownSelectMenuFactory(
    private val townService: TownService,
    private val universitySelectMenuFactory: UniversitySelectMenuFactory,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int) : Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)

        val towns = townService.getTownPage(pageable)

        val buttons = matrix<Button> {
            towns.content
                .map(::makeTownPage)
                .forEach { row(it) }

            val navigation = makePagesNavigationRow(towns) { nextPage, session ->
                session.menuHistory.pushNextMenu(create(nextPage))
            }
            add(navigation)

            row(makeReturnButton())
        }

        val message = "Выбери город, в котором ты ищешь университет!"

        return Menu(buttons, message)
    }

    private fun makeTownPage(town: TownDto) = Button(town.title, uuidAsByteString(town.id)) { session ->
        val nextMenu = universitySelectMenuFactory.create(0, town.id)
        session.menuHistory.pushNextMenu(nextMenu)
    }
}