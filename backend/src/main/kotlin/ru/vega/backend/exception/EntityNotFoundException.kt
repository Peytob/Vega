package ru.vega.backend.exception

class EntityNotFoundException(
    message: String
) : Exception(message) {

    constructor(id: String, type: String) : this("$type with id $id not found")
}