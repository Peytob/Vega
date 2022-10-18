package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.buttons.Matrix
import ru.vega.telegram.menu.Button

interface Menu {

    val buttons: Matrix<Button>

    val message: String
}
