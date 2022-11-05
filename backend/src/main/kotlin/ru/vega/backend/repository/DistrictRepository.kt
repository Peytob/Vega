package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity
import java.util.*

@Repository
interface DistrictRepository : JpaRepository<DistrictEntity, UUID> {
    fun findByTown(town: TownEntity, pageable: Pageable): Page<DistrictEntity>
}
