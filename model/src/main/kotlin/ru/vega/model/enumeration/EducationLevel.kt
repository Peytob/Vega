package ru.vega.model.enumeration

enum class EducationLevel(
    val code: String,
    val title: String
) {
    MIDDLE_PROFESSION("01", "Профессия среднего общего образования"),
    MIDDLE_SPECIALITY("02", "Специальность среднего общего образования"),
    UNDERGRADUATE("03", "Бакалавриат"),
    SPECIALITY("04", "Специалитет"),
    MASTER("05", "Магистратура"),
    OTHER("", "Профессия")
}
