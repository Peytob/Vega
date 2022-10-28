package ru.vega.telegram.menu.processor

import com.benasher44.uuid.bytes
import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.entity.Session
import java.util.UUID

internal fun uuidAsByteString(id: UUID) =
    String(id.bytes)

internal fun makeReturnButton() =
    Button("Назад", "return") {
        it.menuHistory.moveBack()
    }

internal fun makeDisciplinesButtonsMatrix(disciplines: Collection<DisciplineDto>, buttonCallback: (DisciplineDto, Session) -> Unit) =
    disciplines
        .map {
            Button(it.title, uuidAsByteString(it.id)) { session -> buttonCallback(it, session) }
        }
        .chunked(3)
