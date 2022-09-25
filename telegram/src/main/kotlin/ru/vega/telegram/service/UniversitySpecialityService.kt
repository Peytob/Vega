package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

interface UniversitySpecialityService {

    fun getByDisciplinesSet(disciplinesSet: DisciplinesSetDto, pageable: Pageable): Page<UniversitySpecialityDto>

    fun getByExternalId(externalId: String): UniversitySpecialityDto?
}
