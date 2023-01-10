package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.TownEntity
import ru.vega.backend.entity.UniversityEntity
import ru.vega.model.enumeration.EducationGrade
import java.util.*

interface UniversityCrudService {

    fun getPage(titleFilter: String, pageable: Pageable, grade: EducationGrade): Page<UniversityEntity>

    fun getPage(titleFilter: String, pageable: Pageable): Page<UniversityEntity>

    fun getById(id: UUID): UniversityEntity?
    fun getPageByTown(town: TownEntity, pageable: Pageable): Page<UniversityEntity>
}