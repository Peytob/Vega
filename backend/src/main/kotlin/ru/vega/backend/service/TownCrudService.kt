package ru.vega.backend.service

import ru.vega.backend.entity.DistrictEntity

interface TownCrudService {

    fun getDistrictByExternalId(districtId: String): DistrictEntity?
}
