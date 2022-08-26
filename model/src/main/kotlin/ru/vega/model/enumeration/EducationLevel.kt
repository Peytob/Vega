package ru.vega.model.enumeration

enum class EducationLevel(
    val code: String,
    val title: String
) {
    UNDERGRADUATE("03", "Бакалавриат"),
    SPECIALITY("04", "Специалитет"),
    MASTER("05", "Магистратура")
}
