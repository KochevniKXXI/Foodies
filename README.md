# Foodies

Тестовое задание для [REQUEST](https://requestdesign.ru/)

## Модули

[:app](app/) — Модуль приложения. Содержит в себе навигационный граф и общее состояние приложения.

[:core:data](core/data/) — Модуль уровня данных. Содержит репозитории.

[:core:designsystem](core/designsystem/) — Модуль уровня пользовательского интерфейса. Содержит тему
приложения, необходимые компоненты пользовательского интерфейса, ресурсы и расширения.

[:core:model](core/model/) — Модуль, содержащий модели данных, используемых в приложении.

[:core:network](core/network/) — Модуль уровня данных. Содержит источник сетевых данных.

[:core:runtime](core/runtime/) — Модуль уровня данных. Содержит источник данных, существующих только
во время выполнения.

[:feature](feature/) — Содержит модули уровня пользовательского интерфейса для каждого экрана
приложения. В каждом из них описан интерфейс, ViewModel, а также способ доступа к экрану из NavHost.
