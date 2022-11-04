package ru.vega.model.dto.speciality

import ru.vega.model.enumeration.EducationLevel
import java.util.*

data class SpecialityDto(
    val id: UUID,
    val title: String,
    val code: String,
    val description: String,
    val educationLevel: EducationLevel
)
