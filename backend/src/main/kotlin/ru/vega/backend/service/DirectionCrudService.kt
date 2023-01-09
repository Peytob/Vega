package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DirectionEntity
import java.util.*

interface DirectionCrudService {

    fun getPage(titleFilter: String, pageable: Pageable): Page<DirectionEntity>

    fun getById(id: UUID): DirectionEntity?
}
