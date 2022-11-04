package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.UniversityService
import java.util.*

@Component
@EnableConfigurationProperties(MenuProperties::class)
class UniversitySelectMenuFactory(
    private val universityService: UniversityService,
    private val universityDetailsMenuFactory: UniversityDetailsMenuFactory,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int, townId: UUID): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)
        val universities = universityService.getByTown(townId, pageable)

        val buttons = matrix<Button> {

            universities.content
                .map(::makeUniversityButton)
                .forEach { row(it) }

            val navigation = makePagesNavigationRow(universities) { nextPage, session ->
                val nextMenu = create(nextPage, townId)
                session.menuHistory.pushNextMenu(nextMenu)
            }
            add(navigation)

            row(makeReturnButton())
        }

        val message = "Какой университет тебя интересует?"

        return Menu(buttons, message)
    }

    private fun makeUniversityButton(university: UniversityDto) = Button(university.title, uuidAsByteString(university.id)) { session ->
        val nextMenu = universityDetailsMenuFactory.create(university.id)
        session.menuHistory.pushNextMenu(nextMenu)
    }

}
