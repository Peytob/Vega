package ru.vega.model.dto.speciality

import ru.vega.model.enumeration.EducationLevel

data class SpecialityDto(
    val externalId: String,
    val title: String,
    val code: String,
    val description: String,
    val educationLevel: EducationLevel
)
