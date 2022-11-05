package ru.vega.backend.service

import com.google.common.base.Suppliers
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import ru.vega.backend.entity.DisciplineEntity
import ru.vega.backend.entity.DisciplinesSetEntity
import ru.vega.backend.repository.DisciplinesSetRepository
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class DisciplinesSetCrudServiceImpl(
    private val disciplinesSetRepository: DisciplinesSetRepository
) : DisciplinesSetCrudService {

    // TODO Попробовать перенести на SQL + меморизацию результата запроса

    private val memorizedDisciplinesSetList = Suppliers.memoizeWithExpiration({
        val disciplinesSets = disciplinesSetRepository.findAll()
        disciplinesSets.sortBy { it.disciplines!!.size }
        disciplinesSets
    }, 20, TimeUnit.MINUTES)

    override fun findDisciplinesSetByDisciplines(disciplines: Collection<DisciplineEntity>): Collection<DisciplinesSetEntity> {
        val disciplinesSetList = memorizedDisciplinesSetList.get()
        return disciplinesSetList
            .filter { it.disciplines?.size == disciplines.size && it.disciplines?.containsAll(disciplines) ?: false }
    }

    override fun getById(id: UUID): DisciplinesSetEntity? =
        disciplinesSetRepository.findByIdOrNull(id)
}
