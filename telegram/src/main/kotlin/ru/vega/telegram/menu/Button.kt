package ru.vega.telegram.menu

import ru.vega.telegram.model.entity.Session

typealias ButtonId = String

data class Button(
    val text: String,
    val id: ButtonId,
    val callback: (session: Session) -> Unit
)
