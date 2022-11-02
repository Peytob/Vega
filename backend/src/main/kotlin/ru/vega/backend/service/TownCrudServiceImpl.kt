package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity
import ru.vega.backend.repository.DistrictRepository
import ru.vega.backend.repository.TownRepository
import java.util.UUID

@Service
class TownCrudServiceImpl(
    private val districtRepository: DistrictRepository,
    private val townRepository: TownRepository
) : TownCrudService {

    override fun getDistrictById(districtId: UUID): DistrictEntity? =
        districtRepository.findByIdOrNull(districtId)

    override fun getTownPage(pageable: Pageable): Page<TownEntity> =
        townRepository.findAll(pageable)

    override fun getTownByExternalId(townId: String): TownEntity? =
        townRepository.getByExternalId(townId)

    override fun getDistrictPageByTown(town: TownEntity, pageable: Pageable): Page<DistrictEntity> =
        districtRepository.findByTown(town, pageable)
}