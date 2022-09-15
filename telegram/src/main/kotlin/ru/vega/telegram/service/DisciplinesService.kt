package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplineDto

interface DisciplinesService {

    fun getAll(): Collection<DisciplineDto>
    fun getByExternalId(externalId: String): DisciplineDto?
}
