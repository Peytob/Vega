package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.vega.backend.entity.SpecialityEntity
import ru.vega.backend.repository.SpecialityRepository

@Service
class SpecialityCrudServiceImpl(
    private val specialityRepository: SpecialityRepository
) : SpecialityCrudService {

    override fun getByExternalId(id: String): SpecialityEntity? =
        specialityRepository.findByExternalId(id)

    override fun getPage(titleFilter: String, pageable: Pageable): Page<SpecialityEntity> =
        specialityRepository.findAllByTitleContainingIgnoreCase(titleFilter, pageable)
}