package ru.vega.telegram.menu.processor

import dev.inmo.tgbotapi.utils.matrix
import dev.inmo.tgbotapi.utils.row
import org.springframework.stereotype.Component
import ru.vega.telegram.menu.Button
import ru.vega.telegram.model.Menu
import java.net.URI

@Component
class ServicesMenuFactory(
    private val justMessageMenuFactory: JustMessageMenuFactory
): MenuFactory {

    // TODO Вынести в БД!!!

    companion object {
        val MENTORING = """
            Летнее наставничество
            Предоставление информации и индивидуальной аналитики по поступлению: с начала приёмной компании и до публикации списков поступивших (процент шансов поступления по каждой специальности, на которую вы подали документы и рекомендации, с каждым обновлением списков).
            500 р.

            Наставничество до приемной кампании
            Любые вопросы по поступлению, определение вектора развития, помощь в подготовке к экзаменам и тд + скидка 10% на летнее наставничество
            от 150 р./мес.
        """.trimIndent()

        val TUTORING = """
            Размещение информации о репетиторских услугах в боте.
            Доступные подписки:
            1 месяц - 250р
            6 месяцев - 750р
            12 месяцев - 1000р
        """.trimIndent()

        val SPAM = """
            Рекламная рассылка вашего сообщения абитуриентам в наших сообществах Telegram и ВК.
            700р
        """.trimIndent()

        val CHAT = """
            Мы создадим чаты между студентами вашего учебного заведения и абитуриентами. В этих чатах абитуриенты смогут в неформальной форме спросить все интересующие их вопросы.
            500р
        """.trimIndent()
    }

    fun create(contact: URI): Menu {
        val contactButtons = listOf(Button("Обратиться за услугой", "tg", url = contact) {})
        val buttons = matrix<Button> {
            row(Button("Наставничество", "mentoring") { session ->
                val menu = justMessageMenuFactory.create(MENTORING, contactButtons)
                session.menuHistory.pushNextMenu(menu)
            })
            row(Button("Размещение информации о репетиторских услугах", "tutoring") { session ->
                val menu = justMessageMenuFactory.create(TUTORING, contactButtons)
                session.menuHistory.pushNextMenu(menu)
            })
            row(Button("Рекламная рассылка", "spam") { session ->
                val menu = justMessageMenuFactory.create(SPAM, contactButtons)
                session.menuHistory.pushNextMenu(menu)
            })
            row(Button("Создания чата между учебным заведением и абитуриентами", "chat") { session ->
                val menu = justMessageMenuFactory.create(CHAT, contactButtons)
                session.menuHistory.pushNextMenu(menu)
            })
            row(makeReturnButton())
        }

        val message = "Предоставляемые нами услуги"

        return Menu(buttons, message, disablePagePreview = false)
    }
}
