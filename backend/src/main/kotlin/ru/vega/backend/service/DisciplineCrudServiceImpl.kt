package ru.vega.backend.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.repository.DisciplineRepository
import ru.vega.model.enumeration.EducationGrade
import java.util.*

@Service
class DisciplineCrudServiceImpl(
    private val disciplineRepository: DisciplineRepository
) : DisciplineCrudService {

    override fun getByAllEducationGrade(educationGrade: EducationGrade): Collection<DisciplineEntity> =
        disciplineRepository.findAllByGrade(educationGrade)

    override fun getById(id: UUID): DisciplineEntity? =
        disciplineRepository.findByIdOrNull(id)

    override fun getById(disciplinesIds: Collection<UUID>): Collection<DisciplineEntity> =
        disciplineRepository.findAllById(disciplinesIds)
}