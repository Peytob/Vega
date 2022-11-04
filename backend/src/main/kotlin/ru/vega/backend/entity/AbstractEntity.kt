package ru.vega.backend.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity(
    @get:Id
    @get:GeneratedValue
    @get:Type(type = "uuid-char")
    open var id: UUID? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractEntity

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}
