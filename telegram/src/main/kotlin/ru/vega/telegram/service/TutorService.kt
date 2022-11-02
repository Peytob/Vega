package ru.vega.telegram.service

import ru.vega.model.dto.tutor.TutorDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.UUID

interface TutorService {

    fun getTutorsByDisciplineAndDistrict(disciplineId: UUID, districtId: UUID, pageable: Pageable): Page<TutorDto>

    fun getTutorById(tutorId: String): TutorDto?

    fun getOnlineTutorsByDiscipline(disciplineId: UUID, pageable: Pageable): Page<TutorDto>
}
