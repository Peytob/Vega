package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.TownEntity
import ru.vega.backend.entity.UniversityEntity
import ru.vega.model.enumeration.EducationGrade
import java.util.UUID

@Repository
interface UniversityRepository : JpaRepository<UniversityEntity, UUID> {

    fun findAllByTitleContainingIgnoreCaseAndGrade(titleFilter: String, pageable: Pageable, grade: EducationGrade): Page<UniversityEntity>

    fun findByTown(town: TownEntity, pageable: Pageable): Page<UniversityEntity>

    fun findAllByTitleContainingIgnoreCase(titleFilter: String, pageable: Pageable): Page<UniversityEntity>
}
