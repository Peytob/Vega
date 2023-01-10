package ru.vega.telegram.menu.processor

import org.springframework.stereotype.Component
import ru.vega.telegram.service.UniversitySpecialityService

@Component
class SpecialityUniversitiesMenuFactory(
    private val specialityService: UniversitySpecialityService
) : MenuFactory {

//    fun create(page: Int, direction: DirectionDto): Menu {
////        specialityService.
//    }
}
