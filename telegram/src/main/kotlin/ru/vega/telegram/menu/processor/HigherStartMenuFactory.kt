package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.vega.telegram.configuration.ExternalResourcesProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService

// TODO Как-то декомпозировать, тут слишком много всего

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name= ["start-menu"], havingValue = "HIGHER")
class HigherStartMenuFactory(
    private val disciplinesService: DisciplinesService,
    private val disciplineDetailsSelectMenuFactory: DisciplineDetailsSelectMenuFactory,
    private val questionSelectMenuFactory: QuestionSelectionMenuFactory,
    private val specialitiesDisciplinesSelectMenuFactory: SpecialitiesDisciplinesSelectMenuFactory,
    private val tutorMeetingTypeSelectMenuFactory: TutorMeetingTypeSelectMenuFactory,
    private val universitiesTownSelectMenuFactory: UniversitiesTownSelectMenuFactory,
    private val universitySpecialityBookmarksMenuFactory: UniversitySpecialitiesBookmarksMenuFactory,
    private val contactsMenuFactory: ContentsMenuFactory,
    private val servicesMenuFactory: ServicesMenuFactory,
    private val externalResourcesProperties: ExternalResourcesProperties
) : StartMenuFactory {

    override fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines") { session ->
                    val disciplines = disciplinesService.getHigher()
                    session.menuHistory.pushNextMenu(disciplineDetailsSelectMenuFactory.create(disciplines))
                },

                Button("Специальности", "specialities") { session ->
                    session.menuHistory.pushNextMenu(specialitiesDisciplinesSelectMenuFactory.create(session.speciality.selectedDisciplines))
                }
            )

            row(
                Button("Репетиторы", "tutors") { session ->
                    session.menuHistory.pushNextMenu(tutorMeetingTypeSelectMenuFactory.create())
                }
            )

            row(
                Button("FAQ", "faq") { session ->
                    session.menuHistory.pushNextMenu(questionSelectMenuFactory.create(0))
                },

                Button("Университеты", "universities") { session ->
                    session.menuHistory.pushNextMenu(universitiesTownSelectMenuFactory.create(0))
                }
            )

            row(
                Button("Контакты", "contacts") { session ->
                    session.menuHistory.pushNextMenu(contactsMenuFactory.create())
                },

                Button("Избранное", "bookmarks") { session ->
                    val menu = universitySpecialityBookmarksMenuFactory.create(session.user.id)
                    session.menuHistory.pushNextMenu(menu)
                }
            )

            row(Button("Если ты поступаешь в ССУЗ", "spo", url = externalResourcesProperties.spoBotUrl) {})

            row(Button("Наши услуги", "services") { session ->
                val menu = servicesMenuFactory.create(externalResourcesProperties.serviceContact)
                session.menuHistory.pushNextMenu(menu)
            })
        }

        val message =
            """
                Приветствую! Меня зовут Vega 💫
                Я хочу помочь тебе в выборе твоей будущей специальности и ВУЗа! Что ты хочешь узнать?
            """.trimIndent()

        return Menu(buttons, message)
    }
}