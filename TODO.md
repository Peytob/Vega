# TODO List

## Backend

### ***Spring Security***

Важно! Добавить авторизацию посредствам Spring Security!

## Telegram

### Разнести MenuFactory

Классы-наследники MenuFactory реализуют большое количество логики и требуют серьезную декомпозицию.

Необходима декомпозиция в виде разноса коллбеков кнопочек и конструирования меню, вероятно - вынос логики создания сообщений.
