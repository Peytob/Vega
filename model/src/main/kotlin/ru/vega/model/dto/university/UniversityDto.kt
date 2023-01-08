package ru.vega.model.dto.university

import ru.vega.model.enumeration.EducationGrade
import java.util.*

data class UniversityDto(
    val id: UUID,
    val title: String,
    val shortTitle: String,
    val description: String,
    val address: String,
    val site: String,
    val town: String,
    val grade: EducationGrade
)
