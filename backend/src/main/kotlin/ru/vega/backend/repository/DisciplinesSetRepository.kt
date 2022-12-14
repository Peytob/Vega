package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.DisciplinesSetEntity
import java.util.*

@Repository
interface DisciplinesSetRepository : JpaRepository<DisciplinesSetEntity, UUID>
