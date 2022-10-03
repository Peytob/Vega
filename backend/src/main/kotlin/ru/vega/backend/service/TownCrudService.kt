package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity

interface TownCrudService {

    fun getDistrictByExternalId(districtId: String): DistrictEntity?
    fun getTownPage(pageable: Pageable): Page<TownEntity>
}
