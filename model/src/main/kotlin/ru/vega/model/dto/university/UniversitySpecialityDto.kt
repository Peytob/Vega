package ru.vega.model.dto.university

import ru.vega.model.dto.speciality.SpecialityDto

data class UniversitySpecialityDto(
    val speciality: SpecialityDto,
    val university: UniversityDto,
    val budgetPlaces: Int?,
    val contractPlaces: Int?,
    val budgetPassingScore: Int?,
    val contractPassingScore: Int?,
    val absentiaPrice: Int?,
    val intramuralPrice: Int?,
    val partTimePrice: Int?
)
