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

    @Query(
        value = """
            SELECT *
            FROM TUTOR_DISCIPLINE_LINK AS tdl 
            INNER JOIN TUTOR AS t 
                ON t.ID = tdl.TUTOR_ID 
            INNER JOIN DISCIPLINE d
                ON d.ID = tdl.DISCIPLINE_ID 
            WHERE t.DISTRICT_ID = :district AND tdl.DISCIPLINE_ID = :discipline 
        """,

        countQuery = """
            SELECT tdl.*, t.ID
            FROM TUTOR_DISCIPLINE_LINK AS tdl 
            INNER JOIN TUTOR AS t 
                ON t.ID = tdl.TUTOR_ID 
            WHERE t.DISTRICT_ID = :district AND tdl.DISCIPLINE_ID = :discipline 
        """,

        nativeQuery = true)
    fun findByDistrictAndDiscipline(district: DistrictEntity, discipline: DisciplineEntity, pageable: Pageable): Page<TutorEntity>

    fun findByExternalId(externalId: String): TutorEntity?
}