package ru.vega.backend.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.repository.DisciplineRepository
import java.util.*

@Service
class DisciplineCrudServiceImpl(
    private val disciplineRepository: DisciplineRepository
) : DisciplineCrudService {

    override fun getAll(): Collection<DisciplineEntity> =
        disciplineRepository.findAll()

    override fun getById(id: UUID): DisciplineEntity? =
        disciplineRepository.findByIdOrNull(id)

    override fun getById(disciplinesIds: Collection<UUID>): Collection<DisciplineEntity> =
        disciplineRepository.findAllById(disciplinesIds)
}