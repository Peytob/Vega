package ru.vega.backend.service

import org.springframework.stereotype.Service
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.repository.DisciplineRepository

@Service
class DisciplineCrudServiceImpl(
    private val disciplineRepository: DisciplineRepository
) : DisciplineCrudService {

    override fun getAll(): Collection<DisciplineEntity> =
        disciplineRepository.findAll()

    override fun getByExternal(id: String): DisciplineEntity? =
        disciplineRepository.findByExternalId(id)
}