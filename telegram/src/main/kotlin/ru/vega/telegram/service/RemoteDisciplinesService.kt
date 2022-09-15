package ru.vega.telegram.service

import com.google.common.base.Suppliers
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.discipline.DisciplineDto

@Service
class RemoteDisciplinesService(
    private val restTemplate: RestTemplate
) : DisciplinesService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    private val disciplinesCache = Suppliers.memoize {
        logger.info("Updating disciplines cache")

        restTemplate
            .getForObject("/discipline", Array<DisciplineDto>::class.java)!!
            .associateBy(DisciplineDto::externalId)
    }

    override fun getAll(): Collection<DisciplineDto> {
        return disciplinesCache.get().values
    }

    override fun getByExternalId(externalId: String): DisciplineDto? {
        return disciplinesCache.get()[externalId]
    }
}
