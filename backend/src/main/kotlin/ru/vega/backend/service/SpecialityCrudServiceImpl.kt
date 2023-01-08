package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.SpecialityEntity
import ru.vega.backend.repository.SpecialityRepository
import java.util.*

@Service
class SpecialityCrudServiceImpl(
    private val specialityRepository: SpecialityRepository
) : SpecialityCrudService {

    override fun getById(id: UUID): SpecialityEntity? =
        specialityRepository.findByIdOrNull(id)

    override fun getPage(titleFilter: String, pageable: Pageable): Page<SpecialityEntity> =
        specialityRepository.findAllByTitleContainingIgnoreCase(titleFilter, pageable)

    override fun getPageByDirection(directionId: UUID, pageable: Pageable): Page<SpecialityEntity> =
        specialityRepository.findAllByDirectionContainingIgnoreCase(directionId, pageable)
}