package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.enumeration.TownType
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.TownService

@Component
class SelectSpecifiedTownMenuFactory(
    private val townService: TownService,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int, townType: TownType): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val middleTownsPage = townService.getMiddleTownsPage(pageable, townType)

        val buttons = matrix<Button> {

            middleTownsPage
                .content
                .map {
                    Button(it.title, uuidAsByteString(it.id)) {
                    }
                }
                .forEach(::row)

            add(makePagesNavigationRow(middleTownsPage) { page, session ->
                session.menuHistory.pushNextMenu(create(page, townType))
            })

            row(makeReturnButton())
        }

        val message = "Где ты ищешь ССУЗ?"

        return Menu(buttons, message)
    }

}
