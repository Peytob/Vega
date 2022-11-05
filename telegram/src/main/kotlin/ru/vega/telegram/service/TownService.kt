package ru.vega.telegram.service

import ru.vega.model.dto.town.DistrictDto
import ru.vega.model.dto.town.TownDto
import ru.vega.model.utils.Page
import ru.vega.model.utils.Pageable
import java.util.*

interface TownService {

    fun getTownPage(pageable: Pageable): Page<TownDto>

    fun getDistrictPage(townId: UUID, pageable: Pageable): Page<DistrictDto>
}
