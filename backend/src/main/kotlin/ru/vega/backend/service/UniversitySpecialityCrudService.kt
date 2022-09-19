package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.backend.entity.UniversitySpecialityEntity

interface UniversitySpecialityCrudService {

    fun getByDisciplineSet(disciplinesSet: DisciplinesSetEntity, pageable: Pageable): Page<UniversitySpecialityEntity>
}
