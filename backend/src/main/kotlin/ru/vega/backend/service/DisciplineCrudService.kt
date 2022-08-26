package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity

interface DisciplineCrudService {

    fun getAll(): Collection<DisciplineEntity>

    fun getByExternal(id: String): DisciplineEntity?
}