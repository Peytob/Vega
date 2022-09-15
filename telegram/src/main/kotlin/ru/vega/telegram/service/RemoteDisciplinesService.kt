package ru.vega.telegram.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.discipline.DisciplineDto

@Service
class RemoteDisciplinesService(
    private val restTemplate: RestTemplate
) : DisciplinesService {

    // TODO: Caching
    override fun getAll(): Collection<DisciplineDto> {
        return restTemplate.getForObject("/discipline", Array<DisciplineDto>::class.java)!!.toList()
    }
}