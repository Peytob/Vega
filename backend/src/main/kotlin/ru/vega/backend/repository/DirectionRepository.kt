package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.DirectionEntity
import java.util.*

@Repository
interface DirectionRepository : JpaRepository<DirectionEntity, UUID> {
    fun findAllByTitleContainingIgnoreCase(titleFilter: String, pageable: Pageable): Page<DirectionEntity>
}
