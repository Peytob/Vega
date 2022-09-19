package ru.vega.backend.service

import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.entity.DisciplinesSetEntity

interface DisciplinesSetCrudService {

    fun findDisciplinesSetByDisciplines(disciplines: Collection<DisciplineEntity>): Collection<DisciplinesSetEntity>

    fun getByExternalId(externalId: String): DisciplinesSetEntity?
}