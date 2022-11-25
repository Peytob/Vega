package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.TownService
import java.util.*

@Component
@EnableConfigurationProperties(MenuProperties::class)
class TutorDistrictSelectMenuFactory(
    private val townService: TownService,
    private val tutorResultMenuFactory: TutorResultMenuFactory,
    private val menuProperties: MenuProperties
) : MenuFactory {

    fun create(page: Int, townId: UUID): Menu {
        val pageable = Pageable(page, menuProperties.itemsPerPage)

        val districts = townService.getDistrictPage(townId, pageable)

        val buttons = matrix<Button> {
            districts.content
                .map(::makeDistrictButton)
                .forEach { row(it) }

            val navigation = makePagesNavigationRow(districts) { nextPage, session ->
                val nextMenu = create(nextPage, townId)
                session.menuHistory.changeCurrentMenu(nextMenu)
            }
            add(navigation)

            row(makeReturnButton())
        }

        val message = if (districts.empty)
                "Ой-ой. Мы, пока что, не знаем репетиторов в этом городе :( Следи за обновлениями, скоро появятся"
            else
                "Выбери район города, в котором ты ищешь репетитора!"

        return Menu(buttons, message)
    }

    private fun makeDistrictButton(district: DistrictDto) = Button(district.title, uuidAsByteString(district.id)) { session ->
        session.tutor.districtId = district.id
        val nextMenu = tutorResultMenuFactory.create(0, session.tutor)
        session.menuHistory.pushNextMenu(nextMenu)
    }
}
