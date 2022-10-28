package ru.vega.telegram.menu.processor

import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.entity.Session

internal fun makeDisciplinesButtonsMatrix(disciplines: Collection<DisciplineDto>, buttonCallback: (DisciplineDto, Session) -> Unit) =
    disciplines
        .map {
            Button(it.title, it.title.hashCode().toString()) { session -> buttonCallback(it, session) }
        }
        .chunked(3)
