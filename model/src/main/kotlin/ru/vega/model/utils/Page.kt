package ru.vega.model.utils

// Just to using in bots, without spring data library
data class Page<T>(
    val content: Collection<T>,
    val totalPages: Int,
    val totalElements: Int,
    val number: Int,

    val last: Boolean,
    val first: Boolean,
    val empty: Boolean
) {
    companion object {
        fun <T> empty(): Page<T> =
            Page(emptyList(), 0, 0, 0, last = true, first = true, empty = true)
    }
}