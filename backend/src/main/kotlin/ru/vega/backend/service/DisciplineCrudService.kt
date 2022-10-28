package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity
import java.util.UUID

interface DisciplineCrudService {

    fun getAll(): Collection<DisciplineEntity>

    @Deprecated("External ids will be removed")
    fun getByExternalId(id: String): DisciplineEntity?

    fun getAllByExternalId(disciplinesExternalIds: Collection<String>): Collection<DisciplineEntity>

    fun getById(id: UUID): DisciplineEntity?
}