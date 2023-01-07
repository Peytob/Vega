package ru.vega.telegram.menu.processor

import ru.vega.telegram.model.Menu

interface StartMenuFactory : MenuFactory {
    fun create(): Menu
}