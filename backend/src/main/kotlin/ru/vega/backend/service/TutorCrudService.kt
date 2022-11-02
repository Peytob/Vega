package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TutorEntity
import java.util.UUID

interface TutorCrudService {

    fun getByDistrictAndDiscipline(district: DistrictEntity, discipline: DisciplineEntity, pageable: Pageable): Page<TutorEntity>
    fun getById(id: UUID): TutorEntity?
    fun getOnlineByDiscipline(discipline: DisciplineEntity, pageable: Pageable): Page<TutorEntity>
}
