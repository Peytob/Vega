package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.DirectionEntity
import ru.vega.backend.entity.SpecialityEntity
import java.util.*

@Repository
interface SpecialityRepository : JpaRepository<SpecialityEntity, UUID> {

    fun findAllByTitleContainingIgnoreCase(titleFilter: String, pageable: Pageable): Page<SpecialityEntity>
    fun findAllByDirection(direction: DirectionEntity, pageable: Pageable): Page<SpecialityEntity>
}
