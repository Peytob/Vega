package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

interface UniversitySpecialityService {

    fun getByDisciplinesSet(disciplinesSet: DisciplinesSetDto, score: Int?, pageable: Pageable): Page<UniversitySpecialityDto>
    fun getById(id: UUID): UniversitySpecialityDto?
}
