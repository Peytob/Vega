package ru.vega.telegram.model

import dev.inmo.tgbotapi.types.buttons.Matrix
import ru.vega.telegram.menu.Button

data class Menu(
    val buttons: Matrix<Button>,
    val message: String
)