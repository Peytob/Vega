package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.entity.DistrictEntity
import ru.vega.backend.entity.TutorEntity
import ru.vega.backend.repository.TutorRepository
import java.util.*

@Service
class TutorCrudServiceImpl(
    private val tutorRepository: TutorRepository
) : TutorCrudService {

    override fun getByDistrictAndDiscipline(
        district: DistrictEntity,
        discipline: DisciplineEntity,
        pageable: Pageable
    ): Page<TutorEntity> =
        tutorRepository.findByDistrictAndDiscipline(district, discipline, pageable)

    override fun getOnlineByDiscipline(
        discipline: DisciplineEntity,
        pageable: Pageable
    ): Page<TutorEntity> =
        tutorRepository.findOnlineByDiscipline(discipline, pageable)

    override fun getById(id: UUID): TutorEntity? = tutorRepository.findByIdOrNull(id)
}