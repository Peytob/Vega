package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DirectionEntity
import ru.vega.backend.entity.SpecialityEntity
import java.util.*

interface SpecialityCrudService {

    fun getById(id: UUID): SpecialityEntity?

    fun getPage(titleFilter: String, pageable: Pageable): Page<SpecialityEntity>

    fun getPageByDirection(direction: DirectionEntity, pageable: Pageable): Page<SpecialityEntity>
}
