package ru.vega.telegram.menu.processor

import com.benasher44.uuid.bytes
import dev.inmo.tgbotapi.utils.row
import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.model.utils.Page
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.entity.Session
import java.util.*

private const val RETURN_BUTTON_TEXT = "↩"
private const val SELECTED_SYMBOL = "✅"

private const val FIRST_PAGE_DECREMENT_BUTTON_TEXT = "⏮"
private const val DECREMENT_BUTTON_TEXT = "⏪"
private const val LAST_PAGE_INCREMENT_BUTTON_TEXT = "⏭"
private const val PAGE_INCREMENT_BUTTON_TEXT = "⏩"

private val RETURN_BUTTON = Button(RETURN_BUTTON_TEXT, "return") {
    it.menuHistory.moveBack()
}

internal fun uuidAsByteString(id: UUID) =
    String(id.bytes)

internal fun makeReturnButton() = RETURN_BUTTON

internal fun makeDisciplinesButtonsMatrix(
    disciplines: Collection<DisciplineDto>,
    markAsSelected: Collection<UUID> = emptyList(),
    buttonCallback: (DisciplineDto, Session) -> Unit) =
    disciplines
        .map {
            val title = if (markAsSelected.contains(it.id)) {
                "$SELECTED_SYMBOL ${it.title}"
            } else {
                it.title
            }

            Button(title, uuidAsByteString(it.id)) { session -> buttonCallback(it, session) }
        }
        .chunked(3)

internal fun makePagesNavigationRow(page: Page<*>, buttonCallback: (Int, Session) -> Unit) = row<Button> {
    if (page.totalPages == 1) {
        return@row
    }

    if (page.first) {
        add(
            Button(FIRST_PAGE_DECREMENT_BUTTON_TEXT, "\$p_last") { buttonCallback(0, it) }
        )
    } else {
        add(
            Button(DECREMENT_BUTTON_TEXT, "\$p_last") { buttonCallback(page.number.dec(), it) }
        )
    }

    add(
        Button("${page.number.inc()} / ${page.totalPages}", "\$p_cur") { buttonCallback(page.number, it) }
    )

    if (page.last) {
        add(
            Button(LAST_PAGE_INCREMENT_BUTTON_TEXT, "\$p_next") { buttonCallback(page.totalPages.dec(), it) }
        )
    } else {
        add(
            Button(PAGE_INCREMENT_BUTTON_TEXT, "\$p_next") { buttonCallback(page.number.inc(), it) }
        )
    }
}
