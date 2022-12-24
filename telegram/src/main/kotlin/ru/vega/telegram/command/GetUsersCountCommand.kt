package ru.vega.telegram.command

import dev.inmo.tgbotapi.types.ParseMode.HTMLParseMode
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.model.dto.user.TelegramUserDto
import ru.vega.telegram.configuration.TelegramProperties
import ru.vega.telegram.service.MessageService
import ru.vega.telegram.service.UserService

@Component
class GetUsersCountCommand(
    private val userService: UserService,
    private val telegramProperties: TelegramProperties,
    private val messageService: MessageService
) : Command {

    companion object {
        private val logger = LoggerFactory.getLogger(GetUsersCountCommand::class.java)
    }

    override suspend fun execute(message: CommonMessage<*>) {
        if (!telegramProperties.admins.contains(message.chat.id.chatId)) {
            logger.error("Users command from not admin user!")
            return
        }

        val allUsers = userService.getAllUsers()
        val userListMessages = allUsers
            .content
            .mapNotNull(TelegramUserDto::username)
            .chunked(50)
            .map { users -> users.joinToString("; ") }

        messageService.sendMessage(message.chat.id, "Общее количество пользователей в БД: ${allUsers.totalElements}")
        userListMessages.forEach { messageText ->
            messageService.sendMessage(message.chat.id, messageText, parseMode = HTMLParseMode) }
    }

    override fun getCommandString() = "/users"
}