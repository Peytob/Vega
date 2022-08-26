package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.DisciplineEntity
import java.util.*

@Repository
interface DisciplineRepository : JpaRepository<DisciplineEntity, UUID> {

    fun findByExternalId(externalId: String): DisciplineEntity
}
