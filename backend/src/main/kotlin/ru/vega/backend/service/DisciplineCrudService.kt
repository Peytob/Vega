package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity
import java.util.UUID

interface DisciplineCrudService {

    fun getAll(): Collection<DisciplineEntity>

    fun getById(disciplinesIds: Collection<UUID>): Collection<DisciplineEntity>

    fun getById(id: UUID): DisciplineEntity?
}