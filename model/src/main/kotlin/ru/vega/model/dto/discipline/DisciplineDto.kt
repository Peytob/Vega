package ru.vega.model.dto.discipline

import ru.vega.model.enumeration.EducationGrade
import java.util.UUID

data class DisciplineDto(
    val id: UUID,
    val title: String,
    val description: String,
    val fipiLink: String,
    val lastMiddleScore: Int,
    val passingScore: Int,
    val sdamGiaLink: String,
    val descriptionYear: Int,
    val grade: EducationGrade
)
