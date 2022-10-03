package ru.vega.backend.controller

import org.apache.logging.log4j.util.Strings
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.vega.backend.mapper.TownMapper
import ru.vega.backend.service.TownCrudService
import ru.vega.model.dto.town.TownDto
import javax.validation.constraints.Min

@RestController
@RequestMapping("/town")
class TownController(
    private val townCrudService: TownCrudService,
    private val townMapper: TownMapper
) {

    @GetMapping
    fun get(@RequestParam(defaultValue = Strings.EMPTY) filter: String,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) page: Int,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) size: Int,
            @RequestParam(value = "sortDir", defaultValue = "ASC") sortDir: Sort.Direction
    ): ResponseEntity<Page<TownDto>> {
        val pageable = PageRequest.of(page, size, Sort.by(sortDir, "title"))
        val townEntitiesPage = townCrudService.getTownPage(pageable)
        val towns = townEntitiesPage.map(townMapper::toDto)
        return ResponseEntity.ok(towns)
    }
}