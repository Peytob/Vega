package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import ru.vega.telegram.configuration.ExternalResourcesProperties
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import ru.vega.telegram.service.DisciplinesService

@Component
@ConditionalOnProperty(prefix = "telegram.bot", name= ["start-menu"], havingValue = "MIDDLE")
class MiddleStartMenuFactory(
    private val disciplinesService: DisciplinesService,
    private val externalResourcesProperties: ExternalResourcesProperties,
    private val disciplineDetailsSelectMenuFactory: DisciplineDetailsSelectMenuFactory,
    private val directionSelectMenuFactory: DirectionSelectMenuFactory,
    private val tutorMeetingTypeSelectMenuFactory: TutorMeetingTypeSelectMenuFactory,
    private val contactsMenuFactory: ContentsMenuFactory,
    private val townTypeSelectMenyFactory: TownTypeSelectMenyFactory,
    private val servicesMenuFactory: ServicesMenuFactory
) : StartMenuFactory {

    override fun create(): Menu {
        val buttons = matrix<Button> {
            row(
                Button("Предметы", "disciplines9") { session ->
                    val disciplines = disciplinesService.getMiddle()
                    session.menuHistory.pushNextMenu(disciplineDetailsSelectMenuFactory.create(disciplines))
                },
                Button("Специальности", "speciality9") { session ->
                    val menu = directionSelectMenuFactory.create(0)
                    session.menuHistory.pushNextMenu(menu)
                }
            )

            row(
                Button("Репетиторы", "tutors") { session ->
                    session.menuHistory.pushNextMenu(tutorMeetingTypeSelectMenuFactory.create())
                }
            )

            row(
                Button("ССУЗы", "lowqualityeducationsources") { session ->
                    session.menuHistory.pushNextMenu(townTypeSelectMenyFactory.create())
                }
            )

            row(
                Button("Контакты", "contacts") { session ->
                    session.menuHistory.pushNextMenu(contactsMenuFactory.create())
                }
            )

            row(
                Button("Если ты поступаешь в ВУЗ", "universityBot", url = externalResourcesProperties.vegaBot) {}
            )

            row(Button("Наши услуги", "services") { session ->
                val menu = servicesMenuFactory.create(externalResourcesProperties.serviceContact)
                session.menuHistory.pushNextMenu(menu)
            })
        }

        val message =
            """
                Приветствую! Меня зовут Vega 💫
                Я хочу помочь тебе в выборе твоей будущей специальности и ССУЗа! Что ты хочешь узнать?
            """.trimIndent()

        return Menu(buttons, message)
    }
}