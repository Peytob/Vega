package ru.vega.backend.entity

import org.hibernate.annotations.Type
import java.util.*
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class AbstractEntity(
    id: UUID? = null,

    @get:Column(unique = true, nullable = false)
    open var externalId: String? = null
) {
    @get:Id
    @get:GeneratedValue
    @get:Type(type = "uuid-char")
    open var id = id
        set(value) {
            field = value
            externalId = value.toString().substring(19..22)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractEntity

        if (externalId != other.externalId) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = externalId?.hashCode() ?: 0
        result = 31 * result + (id?.hashCode() ?: 0)
        return result
    }
}
