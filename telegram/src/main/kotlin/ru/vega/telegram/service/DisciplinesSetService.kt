package ru.vega.telegram.service

import ru.vega.model.dto.discipline.DisciplinesSetDto

interface DisciplinesSetService {

    fun getDisciplinesSet(disciplinesExternalIds: Set<String>): DisciplinesSetDto?
}
