package ru.vega.telegram.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.speciality.SpecialityDto
import java.util.*

@Service
class RemoteSpecialityService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate
) : SpecialityService {

    override fun getById(id: UUID): SpecialityDto? =
        restTemplate.getForObjectOrNull("/higher/speciality/{id}", SpecialityDto::class.java, id)
}
