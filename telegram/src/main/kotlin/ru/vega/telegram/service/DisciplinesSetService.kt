package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplinesSetDto
import java.util.UUID

interface DisciplinesSetService {

    fun getDisciplinesSet(disciplinesIds: Set<UUID>): DisciplinesSetDto?
}
