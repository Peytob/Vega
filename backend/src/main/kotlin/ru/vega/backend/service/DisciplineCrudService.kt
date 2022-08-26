package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity
import java.util.*

interface DisciplineCrudService {

    fun getAll(): Collection<DisciplineEntity>

    fun getById(id: UUID): DisciplineEntity?
}