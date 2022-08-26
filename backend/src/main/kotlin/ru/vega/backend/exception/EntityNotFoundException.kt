package ru.vega.backend.exception

import java.util.*

class EntityNotFoundException(
    message: String
) : Exception(message) {

    constructor(id: UUID, type: String) : this("$type with id $id not found")
}