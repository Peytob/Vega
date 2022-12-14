package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.telegram.configuration.ExternalResourcesProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu

@Component
@EnableConfigurationProperties(ExternalResourcesProperties::class)
class ContentsMenuFactory(
    private val externalResourcesProperties: ExternalResourcesProperties
) : MenuFactory {

    fun create(): Menu {
        val buttons = matrix<Button> {
            row(Button("Группа в VK", "vk", url = externalResourcesProperties.vkGroup) {})
            row(Button("Наш Telegram-канал", "tg", url = externalResourcesProperties.telegramVegaChat) {})
            row(Button("Чат вопросов и предложений в Telegram", "tg", url = externalResourcesProperties.telegramSuggestionChat) {})
            row(makeReturnButton())
        }

        val message = "Наши контакты"

        return Menu(buttons, message)
    }
}
