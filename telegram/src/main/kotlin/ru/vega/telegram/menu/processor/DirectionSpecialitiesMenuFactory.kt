package ru.vega.telegram.menu.processor

import org.springframework.stereotype.Component
import ru.vega.model.dto.direction.DirectionDto
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.UniversitySpecialityService

@Component
class DirectionSpecialitiesMenuFactory(
    private val specialityService: UniversitySpecialityService
) : MenuFactory {

//    fun create(page: Int, direction: DirectionDto): Menu {
////        specialityService.
//    }
}
