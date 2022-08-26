package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.SpecialityEntity
import ru.vega.backend.repository.SpecialityRepository

@Service
class SpecialityCrudServiceImpl(
    private val specialityRepository: SpecialityRepository
) : SpecialityCrudService {

    override fun getAll(): Collection<SpecialityEntity> =
        specialityRepository.findAll()

    override fun getByExternalId(id: String): SpecialityEntity? =
        specialityRepository.findByExternalId(id)
}