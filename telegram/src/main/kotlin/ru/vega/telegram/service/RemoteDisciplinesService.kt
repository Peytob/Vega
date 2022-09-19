package ru.vega.telegram.service

import com.google.common.base.Suppliers
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.vega.model.dto.discipline.DisciplineDto
import ru.vega.telegram.configuration.CacheProperties
import java.util.concurrent.TimeUnit

@Service
class RemoteDisciplinesService(
    @Qualifier("backendRestTemplate")
    private val restTemplate: RestTemplate,
    cacheProperties: CacheProperties
) : DisciplinesService {

    companion object {
        private val logger = LoggerFactory.getLogger(RemoteDisciplinesService::class.java)
    }

    private val disciplinesCache = Suppliers.memoizeWithExpiration({
            logger.info("Updating disciplines cache")

            restTemplate
                .getForObject("/discipline", Array<DisciplineDto>::class.java)!!
                .associateBy(DisciplineDto::externalId)
        },
        cacheProperties.backendData.toSeconds(), TimeUnit.SECONDS)

    override fun getAll(): Collection<DisciplineDto> =
        disciplinesCache.get().values

    override fun getByExternalId(externalId: String): DisciplineDto? =
        disciplinesCache.get()[externalId]
}
