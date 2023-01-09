package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity
import ru.vega.model.enumeration.EducationGrade
import java.util.UUID

interface DisciplineCrudService {

    fun getById(disciplinesIds: Collection<UUID>): Collection<DisciplineEntity>

    fun getById(id: UUID): DisciplineEntity?

    fun getByAllEducationGrade(educationGrade: EducationGrade): Collection<DisciplineEntity>
    fun getAll(): Collection<DisciplineEntity>
}