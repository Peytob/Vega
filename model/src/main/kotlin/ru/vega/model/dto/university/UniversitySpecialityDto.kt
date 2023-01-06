package ru.vega.model.dto.university

import java.util.*

data class UniversitySpecialityDto(
    val id: UUID,
    val speciality: UUID,
    val university: UUID,
    val disciplinesSets: Collection<UUID>,
    val budgetPlaces: Int?,
    val contractPlaces: Int?,
    val budgetPassingScore: Int?,
    val contractPassingScore: Int?,
    val absentiaPrice: Int?,
    val intramuralPrice: Int?,
    val partTimePrice: Int?
)
