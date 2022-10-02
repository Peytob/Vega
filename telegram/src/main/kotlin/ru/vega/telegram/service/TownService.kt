package ru.vega.telegram.service

import ru.vega.model.dto.town.TownDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

interface TownService {

    fun getPage(pageable: Pageable): Page<TownDto>
}
