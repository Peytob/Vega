package ru.vega.backend.configuration

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(
    info = Info(title = "Vega API", version = "v1"),
)
class SwaggerConfiguration {

    @Bean
    fun restApi(): GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("api")
            .displayName("Vega API")
            .packagesToScan("ru.vega.backend.controller")
            .build()
    }
}
