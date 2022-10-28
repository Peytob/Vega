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

    @Deprecated("External ids will be removed")
    override fun getByExternalId(id: String): DisciplineEntity? =
        disciplineRepository.findByExternalId(id)

    override fun getById(id: UUID): DisciplineEntity? =
        disciplineRepository.findByIdOrNull(id)

    override fun getAllByExternalId(disciplinesExternalIds: Collection<String>): Collection<DisciplineEntity> =
        disciplineRepository.findAllByExternalIdIn(disciplinesExternalIds)
}