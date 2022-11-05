package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import ru.vega.model.dto.tutor.TutorDto
import ru.vega.model.utils.Pageable
import ru.vega.telegram.configuration.MenuProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.model.entity.SessionTutor
import ru.vega.telegram.model.enums.TutorMeetingForm
import ru.vega.telegram.service.TutorService

@Component
@EnableConfigurationProperties(MenuProperties::class)
class TutorResultMenuFactory(
    private val tutorService: TutorService,
    private val menuProperties: MenuProperties,
    private val tutorDetailsMenuFactory: TutorDetailsMenuFactory
) : MenuFactory {

    fun create(page: Int, search: SessionTutor): Menu {

        val (meetingForm, disciplineId, districtId) = search

        if (disciplineId == null) {
            throw IllegalArgumentException("Tutor discipline id should be selected!")
        }

        val pageable = Pageable(page, menuProperties.itemsPerPage)

        val tutors = when (meetingForm) {
            TutorMeetingForm.ONLINE -> tutorService.getOnlineTutorsByDiscipline(disciplineId, pageable)
            TutorMeetingForm.OFFLINE -> {
                if (districtId == null) {
                    throw IllegalArgumentException("For offline tutor searching, district id is required")
                }

                tutorService.getTutorsByDisciplineAndDistrict(disciplineId, districtId, pageable)
            }
            null -> throw IllegalArgumentException("Tutor meeting form should be selected!")
        }

        val buttons = matrix<Button> {
            tutors.content
                .map(::makeTutorButton)
                .forEach { row(it) }

            row(makeReturnButton())
        }

        val message = if (tutors.empty)
            "Репетиторы по твоему запросу не найдены :с"
        else
            "Выбери интересующего тебя репетитора!"

        return Menu(buttons, message)
    }

    fun makeTutorButton(tutor: TutorDto) = Button(tutor.name, uuidAsByteString(tutor.id)) { session ->
        val nextMenu = tutorDetailsMenuFactory.create(tutor.id)
        session.menuHistory.pushNextMenu(nextMenu)
    }
}
