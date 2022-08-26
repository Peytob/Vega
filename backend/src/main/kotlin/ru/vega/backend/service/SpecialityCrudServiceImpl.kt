package ru.vega.backend.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.SpecialityEntity
import ru.vega.backend.repository.SpecialityRepository
import java.util.*

@Service
class SpecialityCrudServiceImpl(
    private val specialityRepository: SpecialityRepository
) : SpecialityCrudService {

    override fun getAll(): Collection<SpecialityEntity> =
        specialityRepository.findAll()

    override fun getById(id: UUID): SpecialityEntity? =
        specialityRepository.findByIdOrNull(id)
}