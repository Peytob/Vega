package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplinesSetDto
import ru.vega.model.dto.university.MiddleSpecialityDto
import ru.vega.model.dto.university.UniversitySpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import ru.vega.telegram.model.enums.EducationForm
import java.util.*

interface UniversitySpecialityService {

    fun search(disciplinesSet: DisciplinesSetDto, score: Int?, educationForm: Set<EducationForm>, pageable: Pageable): Page<UniversitySpecialityDto>
    fun getUniversitySpecialities(universityId: UUID, pageable: Pageable): Page<UniversitySpecialityDto>
    fun getById(id: UUID): UniversitySpecialityDto?
    fun getMiddleSpecialitiesBySpeciality(specialityId: UUID, pageable: Pageable): Page<MiddleSpecialityDto>
}
