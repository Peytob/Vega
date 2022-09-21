package ru.vega.telegram.model.entity

import dev.inmo.tgbotapi.types.UserId

data class Session(
    val telegram_user: UserId,
    val selectedDisciplinesIds: MutableSet<String> = mutableSetOf(),
    var totalScore: Int? = null
)
