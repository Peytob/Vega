package ru.vega.telegram.service

import ru.vega.model.dto.direction.DirectionDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

interface DirectionService {

    fun getDirectionsPage(pageable: Pageable): Page<DirectionDto>
}
