package ru.vega.telegram.service

import ru.vega.model.dto.tutor.TutorDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

interface TutorService {

    fun getTutorsByDisciplineAndDistrict(
        disciplineExternalId: String,
        districtExternalId: String,
        pageable: Pageable): Page<TutorDto>
}
