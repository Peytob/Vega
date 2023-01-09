package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DirectionEntity
import ru.vega.backend.repository.DirectionRepository
import java.util.*

@Service
class DirectionCrudServiceImpl(
    private val directionRepository: DirectionRepository
) : DirectionCrudService {

    override fun getPage(titleFilter: String, pageable: Pageable): Page<DirectionEntity> =
        directionRepository.findAllByTitleContainingIgnoreCase(titleFilter, pageable)

    override fun getById(id: UUID): DirectionEntity? =
        directionRepository.getById(id)
}