package ru.vega.telegram.menu.processor

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.vega.telegram.model.Menu

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name= ["start-menu"], havingValue = "MIDDLE")
class MiddleStartMenuFactory : StartMenuFactory {

    override fun create(): Menu {
        TODO("Not yet implemented")
    }
}