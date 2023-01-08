package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.SpecialityEntity
import java.util.*

@Repository
interface SpecialityRepository : JpaRepository<SpecialityEntity, UUID> {

    fun findAllByTitleContainingIgnoreCase(titleFilter: String, pageable: Pageable): Page<SpecialityEntity>
    fun findAllByDirectionContainingIgnoreCase(directionId: UUID, pageable: Pageable): Page<SpecialityEntity>
}
