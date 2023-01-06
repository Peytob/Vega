package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.types.UserId
import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.vega.telegram.exception.EntityNotFound
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.SpecialityService
import ru.vega.telegram.service.UniversitySpecialityService
import ru.vega.telegram.service.UserService

@Component
class UniversitySpecialitiesBookmarksMenuFactory(
    private val userService: UserService,
    private val universitySpecialityService: UniversitySpecialityService,
    private val specialityService: SpecialityService,
    private val universitySpecialityMenuFactory: UniversitySpecialityMenuFactory
) : MenuFactory {

    companion object {
        private val logger = LoggerFactory.getLogger(UniversitySpecialityService::class.java)
    }

    fun create(userId: UserId): Menu {
        val userBookmarks = userService.getBookmarks(userId.chatId) ?: throw EntityNotFound("User with id $userId not found")

        val buttons = matrix<Button> {
            userBookmarks.mapNotNull {
                val universitySpeciality = universitySpecialityService.getById(it) ?: run {
                    logger.error("University speciality {} not found", it)
                    return@mapNotNull null
                }

                val speciality = specialityService.getById(universitySpeciality.speciality) ?: run {
                    logger.error("Speciality {} not found", universitySpeciality.speciality)
                    return@mapNotNull null
                }

                Button(speciality.title, uuidAsByteString(speciality.id)) { session ->
                    val menu = universitySpecialityMenuFactory.create(it, session.user.id)
                    session.menuHistory.pushNextMenu(menu)
                }
            }
            .forEach(::row)

            row(makeReturnButton())
        }

        val message = if (userBookmarks.isEmpty())
                "Ты можешь пометить понравившиеся специальности, чтобы вернуться к ним позже!"
            else
                "Какая закладка тебе интересна?"

        return Menu(buttons, message)
    }
}
