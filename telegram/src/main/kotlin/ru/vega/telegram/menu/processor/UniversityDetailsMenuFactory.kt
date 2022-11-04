package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.dto.university.UniversityDto
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.UniversityService
import java.net.URI
import java.net.URL
import java.util.*

@Component
class UniversityDetailsMenuFactory(
    private val universityService: UniversityService,
    private val universitySpecialitiesMenuFactory: UniversitySpecialitiesMenuFactory
) : MenuFactory {

    fun create(universityId: UUID): Menu {

        val university = universityService.getById(universityId) ?:
            throw EntityNotFound("University with id $universityId not found")

        val buttons = matrix<Button> {
            val universitySiteButton = Button("Сайт университета", university.site, URI.create(university.site)) { }
            row(universitySiteButton)

            val specialities = Button("Просмотреть специальности", "specialities") { session ->
                val nextMenu = universitySpecialitiesMenuFactory.create(0, universityId)
                session.menuHistory.pushNextMenu(nextMenu)
            }
            row(specialities)

            row(makeReturnButton())
        }

        val message = makeMessage(university)

        return Menu(buttons, message)
    }

    private fun makeMessage(university: UniversityDto) = """
            ${university.title} (${university.shortTitle})
            ${university.description}
            Университет расположен по адресу ${university.address}.
        """.trimIndent()
}
