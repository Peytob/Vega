package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplineDto
import java.util.UUID

interface DisciplinesService {

    fun getAll(): Collection<DisciplineDto>

    fun getById(id: UUID): DisciplineDto?
}
