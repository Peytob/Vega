package ru.vega.telegram.model.menu

import dev.inmo.tgbotapi.types.buttons.InlineKeyboardButtons.InlineKeyboardButton
import dev.inmo.tgbotapi.types.buttons.Matrix

data class Menu(
    val message: String,
    val replyMarkup: Matrix<InlineKeyboardButton>,
    val disableWebPagePreview: Boolean = false
)
