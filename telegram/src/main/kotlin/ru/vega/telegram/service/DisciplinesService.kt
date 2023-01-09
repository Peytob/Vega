package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplineDto
import java.util.UUID

interface DisciplinesService {

    fun getById(id: UUID): DisciplineDto?

    fun getHigher(): Collection<DisciplineDto>

    fun getMiddle(): Collection<DisciplineDto>
}
