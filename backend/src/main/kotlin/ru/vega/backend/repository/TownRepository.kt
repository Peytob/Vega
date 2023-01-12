package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.TownEntity
import ru.vega.model.enumeration.EducationGrade
import ru.vega.model.enumeration.TownType
import java.util.*

@Repository
interface TownRepository : JpaRepository<TownEntity, UUID> {

    @Query("""
        SELECT DISTINCT TOWN_ID, t.* FROM UNIVERSITY u
        JOIN TOWN t ON t.ID = TOWN_ID
        WHERE t."TYPE" = :townType AND u.GRADE = :gradeFilter
    """,

    countQuery = """
        SELECT DISTINCT COUNT(*) FROM UNIVERSITY u
        JOIN TOWN t ON t.ID = TOWN_ID
        WHERE t."TYPE" = :townType AND u.GRADE = :gradeFilter
    """, nativeQuery = true)
    fun findMiddleTowns(pageable: Pageable, gradeFilter: EducationGrade, townType: TownType): Page<TownEntity>

    fun findByType(pageable: Pageable, type: TownType): Page<TownEntity>
}
