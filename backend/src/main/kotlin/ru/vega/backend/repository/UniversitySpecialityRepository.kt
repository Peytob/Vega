package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.backend.entity.UniversityEntity
import ru.vega.backend.entity.UniversitySpecialityEntity
import java.util.*

interface UniversitySpecialityRepository : JpaRepository<UniversitySpecialityEntity, UUID> {

    @Query(
        value = """
            SELECT *
            FROM DISCIPLINES_SET_UNIVERSITY_SPECIALITY_LINK as ds_to_us
            INNER JOIN UNIVERSITY_SPECIALITY AS us
                ON us.ID = ds_to_us.UNIVERSITY_SPECIALITY_ID
            INNER JOIN DISCIPLINES_SET AS ds
                ON ds.ID = ds_to_us.SET_ID
            WHERE
                SET_ID = :disciplinesSet AND
                (:scoreFilter >= us.budget_passing_score AND :includeBudget OR :scoreFilter >= us.contract_passing_score AND :includeContract)
        """,

        countQuery = """
            SELECT COUNT(*)
            FROM DISCIPLINES_SET_UNIVERSITY_SPECIALITY_LINK as ds_to_us
            INNER JOIN UNIVERSITY_SPECIALITY AS us
                ON us.ID = ds_to_us.UNIVERSITY_SPECIALITY_ID
            WHERE
                SET_ID = :disciplinesSet AND
                (:scoreFilter >= us.budget_passing_score AND :includeBudget OR :scoreFilter >= us.contract_passing_score AND :includeContract)
        """,

        nativeQuery = true)
    fun search(
        disciplinesSet: DisciplinesSetEntity,
        scoreFilter: Int,
        includeBudget: Boolean,
        includeContract: Boolean,
        pageable: Pageable
    ): Page<UniversitySpecialityEntity>

    fun findByUniversity(university: UniversityEntity, pageable: Pageable): Page<UniversitySpecialityEntity>
}
