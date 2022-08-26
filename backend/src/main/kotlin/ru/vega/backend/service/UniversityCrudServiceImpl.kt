package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.UniversityEntity
import ru.vega.backend.repository.UniversityRepository

@Service
class UniversityCrudServiceImpl(
    private val universityRepository: UniversityRepository
) : UniversityCrudService {

    override fun getAll(): Collection<UniversityEntity> =
        universityRepository.findAll()

    override fun getByExternalId(id: String): UniversityEntity? =
        universityRepository.findByExternalId(id)
}