package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.model.enumeration.TownType
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.entity.Session

@Component
class TownTypeSelectMenyFactory(

) : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            val townTypeButton = Button("Город", "town") {
                onTownSelected(TownType.TOWN, it)
            }
            row(townTypeButton)

            val pageTypeButton = Button("Станица", "page") {
                onTownSelected(TownType.PAGE, it)
            }
            row(pageTypeButton)

            val villageTypeButton = Button("Село", "village") {
                onTownSelected(TownType.VILLAGE, it)
            }
            row(villageTypeButton)

            val townshipTypeButton = Button("Поселок", "township") {
                onTownSelected(TownType.TOWNSHIP, it)
            }
            row(townshipTypeButton)

            row(makeReturnButton())
        }

        val message = "Чем является ваш населенный пункт?"

        return Menu(buttons, message)
    }

    private fun onTownSelected(townType: TownType, session: Session) {

    }
}
