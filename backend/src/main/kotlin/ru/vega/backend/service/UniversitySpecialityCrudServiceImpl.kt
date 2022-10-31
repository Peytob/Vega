package ru.vega.backend.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.backend.entity.UniversitySpecialityEntity
import ru.vega.backend.repository.UniversitySpecialityRepository
import java.util.UUID

@Service
class UniversitySpecialityCrudServiceImpl(
    private val universitySpecialityRepository: UniversitySpecialityRepository
) : UniversitySpecialityCrudService {

    override fun getByDisciplineSet(disciplinesSet: DisciplinesSetEntity, scoreFilter: Int, pageable: Pageable): Page<UniversitySpecialityEntity> =
        universitySpecialityRepository.findAllByDisciplinesSet(disciplinesSet, scoreFilter, pageable)

    override fun getById(id: UUID): UniversitySpecialityEntity? =
        universitySpecialityRepository.findByIdOrNull(id)
}
