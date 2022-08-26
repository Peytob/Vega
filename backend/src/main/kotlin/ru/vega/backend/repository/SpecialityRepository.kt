package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.SpecialityEntity
import java.util.UUID

@Repository
interface SpecialityRepository : JpaRepository<SpecialityEntity, UUID> {
    fun findByExternalId(id: String): SpecialityEntity?
}
