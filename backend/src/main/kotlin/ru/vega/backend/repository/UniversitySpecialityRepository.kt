package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.backend.entity.UniversitySpecialityEntity
import java.util.UUID

interface UniversitySpecialityRepository : JpaRepository<UniversitySpecialityEntity, UUID> {

    @Query("""
SELECT *
FROM DISCIPLINES_SET_UNIVERSITY_SPECIALITY_LINK as dsusl
INNER JOIN UNIVERSITY_SPECIALITY AS us
	ON us.ID = dsusl.UNIVERSITY_SPECIALITY_ID
INNER JOIN DISCIPLINES_SET AS ds
	ON ds.ID = dsusl.SET_ID
WHERE SET_ID = :disciplinesSet
    """, nativeQuery = true)
    fun findAllByDisciplinesSet(disciplinesSet: DisciplinesSetEntity, pageable: Pageable): Page<UniversitySpecialityEntity>
}
