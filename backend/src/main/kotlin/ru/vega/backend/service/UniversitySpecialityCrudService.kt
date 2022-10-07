package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.backend.entity.UniversitySpecialityEntity

interface UniversitySpecialityCrudService {

    fun getByDisciplineSet(disciplinesSet: DisciplinesSetEntity, scoreFilter: Int, pageable: Pageable): Page<UniversitySpecialityEntity>
    fun getByExternalId(externalId: String): UniversitySpecialityEntity?
}
