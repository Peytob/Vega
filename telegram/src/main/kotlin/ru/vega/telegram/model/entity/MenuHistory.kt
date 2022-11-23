package ru.vega.telegram.model.entity

import ru.vega.telegram.model.Menu
import java.util.*

class MenuHistory {

    private val menuStack: Stack<Menu> = Stack()

    val size
        get() = menuStack.size

    val currentMenu: Menu?
        get() = if (menuStack.empty()) null else menuStack.peek()

    val empty: Boolean
        get() = size == 0

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

    fun moveBack() {
        menuStack.pop()
    }

    fun clear() {
        menuStack.clear()
    }
}
