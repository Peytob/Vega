package ru.vega.telegram.command

import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage

interface Command {

    fun execute(message: CommonMessage<*>)
    fun getCommandString(): String
}