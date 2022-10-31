package ru.vega.model.dto.university

import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.speciality.SpecialityDto
import java.util.UUID

data class UniversitySpecialityDto(
    val id: UUID,
    val speciality: SpecialityDto,
    val university: UniversityDto,
    val disciplinesSets: Collection<DisciplinesSetDto>,
    val budgetPlaces: Int?,
    val contractPlaces: Int?,
    val budgetPassingScore: Int?,
    val contractPassingScore: Int?,
    val absentiaPrice: Int?,
    val intramuralPrice: Int?,
    val partTimePrice: Int?
)
