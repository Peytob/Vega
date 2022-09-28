package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TutorEntity
import java.util.UUID

interface TutorRepository : JpaRepository<TutorEntity, UUID> {

    // TODO Implement me

    @Query(
        value = """
            SELECT * FROM TUTOR
        """,

        countQuery = """
            SELECT COUNT(*) FROM TUTOR
        """,

        nativeQuery = true)
    fun findByDistrictAndDiscipline(district: DistrictEntity, discipline: DisciplineEntity, pageable: Pageable): Page<TutorEntity>

    fun findByExternalId(externalId: String): TutorEntity?
}
