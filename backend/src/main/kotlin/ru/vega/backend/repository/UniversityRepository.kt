package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.UniversityEntity
import java.util.UUID

@Repository
interface UniversityRepository : JpaRepository<UniversityEntity, UUID> {

    fun findByExternalId(externalId: String): UniversityEntity?
}
