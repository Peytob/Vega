package ru.vega.backend.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.DirectionEntity
import ru.vega.backend.entity.MiddleSpecialityEntity
import ru.vega.backend.entity.SpecialityEntity
import java.util.*

@Repository
interface MiddleSpecialityRepository : JpaRepository<MiddleSpecialityEntity, UUID> {
    fun findBySpecialityDirection(direction: DirectionEntity, pageable: Pageable): Page<MiddleSpecialityEntity>
    fun findBySpeciality(speciality: SpecialityEntity, pageable: Pageable): Page<MiddleSpecialityEntity>
}
