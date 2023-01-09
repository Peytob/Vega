package ru.vega.telegram.service

import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.dto.speciality.SpecialityDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.UUID

interface SpecialityService {

    fun getById(id: UUID): SpecialityDto?
    fun getByDirection(direction: DirectionDto, pageable: Pageable): Page<SpecialityDto>
}
