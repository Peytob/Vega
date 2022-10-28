package ru.vega.backend.exception

import java.util.*

class EntityNotFoundException(
    message: String
) : Exception(message) {

    @Deprecated("All external ids will be removed")
    constructor(id: String, type: String) : this("$type with id $id not found")

    constructor(id: UUID, type: String) : this(id.toString(), type)
}