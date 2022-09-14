package ru.vega.telegram.menu

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MenuManager(
    menus: List<MenuHandler>
) {

    companion object {
        private val logger = LogManager.getLogger()
    }

    private val menus =
        menus.associateBy(MenuHandler::id)

    @PostConstruct
    private fun logMenus() {
        logger.info("Menu manager initialized with {} menus.", menus.size)
        val menusList = menus.map(Map.Entry<String, MenuHandler>::key).toList()
        logger.info("Menus list: {}", menusList)
    }

    fun getMenuHandler(menuId: String): MenuHandler? {
        return menus[menuId]
    }
}
