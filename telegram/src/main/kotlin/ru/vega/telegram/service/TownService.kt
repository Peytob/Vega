package ru.vega.telegram.service

import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.dto.town.TownDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable

interface TownService {

    fun getTownPage(pageable: Pageable): Page<TownDto>

    fun getDistrictPage(townExternalId: String, pageable: Pageable): Page<DistrictDto>
}
