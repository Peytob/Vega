package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.repository.DistrictRepository

@Service
class TownCrudServiceImpl(
    private val districtRepository: DistrictRepository
) : TownCrudService {

    override fun getDistrictByExternalId(districtId: String): DistrictEntity? =
        districtRepository.getByExternalId(districtId)
}