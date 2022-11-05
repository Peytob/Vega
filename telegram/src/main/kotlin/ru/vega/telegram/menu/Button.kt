package ru.vega.telegram.menu

import ru.vega.telegram.model.entity.Session
import java.net.URI

typealias ButtonId = String

data class Button(
    val text: String,
    val id: ButtonId,
    val url: URI? = null,
    val callback: (session: Session) -> Unit
)
