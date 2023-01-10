package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.*
import java.util.*

interface UniversitySpecialityCrudService {

    fun search(
        disciplinesSet: DisciplinesSetEntity,
        scoreFilter: Int,
        includeBudget: Boolean,
        includeContract: Boolean,
        pageable: Pageable): Page<UniversitySpecialityEntity>

    fun getById(id: UUID): UniversitySpecialityEntity?

    fun getByUniversity(university: UniversityEntity, pageable: Pageable): Page<UniversitySpecialityEntity>

    fun getMiddleByDirection(direction: DirectionEntity, pageable: Pageable): Page<MiddleSpecialityEntity>
}
