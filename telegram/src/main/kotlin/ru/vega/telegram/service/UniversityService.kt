package ru.vega.telegram.service

import ru.vega.model.dto.university.UniversityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

interface UniversityService {

    fun getByTown(townId: UUID, pageable: Pageable): Page<UniversityDto>
}
