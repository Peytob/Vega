package ru.vega.model.dto.university

import java.util.UUID

data class MiddleSpecialityDto(
    val id: UUID,
    val speciality: UUID,
    val university: UUID,
    val grade9acceptance: Boolean,
    val grade11acceptance: Boolean,
    val middleScore: Float,
    val budgetPlaces: Int,
    val contractPlaces: Int,
    val absentiaPrice: Int,
    val intramuralPrice: Int,
    val partTimePrice: Int
)
