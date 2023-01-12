package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TownEntity
import ru.vega.model.enumeration.EducationGrade
import ru.vega.model.enumeration.TownType
import java.util.*

interface TownCrudService {

    fun getDistrictById(districtId: UUID): DistrictEntity?
    fun getTownPage(pageable: Pageable): Page<TownEntity>
    fun getTownById(townId: UUID): TownEntity?
    fun getDistrictPageByTown(town: TownEntity, pageable: Pageable): Page<DistrictEntity>
    fun getTownsWithUniversities(pageable: Pageable, gradeFilter: EducationGrade, townType: TownType): Page<TownEntity>
    fun getTownsByType(pageable: Pageable, typeFilter: TownType): Page<TownEntity>
}
