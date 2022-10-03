package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity
import ru.vega.backend.repository.DistrictRepository
import ru.vega.backend.repository.TownRepository

@Service
class TownCrudServiceImpl(
    private val districtRepository: DistrictRepository,
    private val townRepository: TownRepository
) : TownCrudService {

    override fun getDistrictByExternalId(districtId: String): DistrictEntity? =
        districtRepository.getByExternalId(districtId)

    override fun getTownPage(pageable: Pageable): Page<TownEntity> =
        townRepository.findAll(pageable)
}