package ru.vega.backend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.vega.backend.entity.TownEntity
import java.util.UUID

@Repository
interface TownRepository : JpaRepository<TownEntity, UUID>
