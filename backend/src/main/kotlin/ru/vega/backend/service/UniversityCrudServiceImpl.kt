package ru.vega.backend.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.vega.backend.entity.UniversityEntity
import ru.vega.backend.repository.UniversityRepository

@Service
class UniversityCrudServiceImpl(
    private val universityRepository: UniversityRepository
) : UniversityCrudService {

    override fun getPage(titleFilter: String, pageable: Pageable) =
        universityRepository.findAllByTitleContainingIgnoreCase(titleFilter, pageable)

    override fun getByExternalId(id: String): UniversityEntity? =
        universityRepository.findByExternalId(id)
}