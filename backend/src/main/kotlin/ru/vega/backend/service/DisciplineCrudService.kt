package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity

interface DisciplineCrudService {

    fun getAll(): Collection<DisciplineEntity>

    fun getByExternalId(id: String): DisciplineEntity?
}