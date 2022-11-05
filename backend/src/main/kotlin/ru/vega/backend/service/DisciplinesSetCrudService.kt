package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.entity.DisciplinesSetEntity
import java.util.UUID

interface DisciplinesSetCrudService {

    fun findDisciplinesSetByDisciplines(disciplines: Collection<DisciplineEntity>): Collection<DisciplinesSetEntity>

    fun getById(id: UUID): DisciplinesSetEntity?
}