package ru.vega.telegram.model.entity

import ru.vega.telegram.menu.processor.Menu
import java.util.*

class MenuHistory {

    private val menuStack: Stack<Menu> = Stack()

    var size = menuStack.size

    var currentMenu = if (menuStack.empty()) null else menuStack.peek()

    var empty = size == 0

    fun pushNextMenu(menu: Menu) {
        menuStack.push(menu)
    }

    fun changeCurrentMenu(menu: Menu) {
        if (menuStack.empty()) {
            menuStack.push(menu)
        } else {
            menuStack[menuStack.size - 1] = menu
        }
    }
}
