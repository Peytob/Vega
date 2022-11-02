package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity
import java.util.*

interface TownCrudService {

    fun getDistrictById(districtId: UUID): DistrictEntity?
    fun getTownPage(pageable: Pageable): Page<TownEntity>
    fun getTownById(townId: UUID): TownEntity?
    fun getDistrictPageByTown(town: TownEntity, pageable: Pageable): Page<DistrictEntity>
}
