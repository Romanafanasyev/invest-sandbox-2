# 📱 Invest Sandbox - Client App
## 📝 Описание

Invest Sandbox — мобильное приложение, позволяющее пользователям обучаться основам инвестирования через виртуальный рынок акций. В приложении можно покупать и продавать акции, просматривать текущий портфель и обновлять цены акций в реальном времени.

## 📂 Структура проекта

- **/activities/AuthorizationActivity.kt**: Экран для авторизации и регистрации пользователей.
- **/activities/StockActivity.kt**: Экран с интерфейсом для просмотра доступных акций, покупки и продажи, а также управления портфелем.
- **/models/Stock.kt**: Модель данных для акций, включая идентификатор, название, цену и количество.
- **/network/ApiService.kt**: Интерфейс для взаимодействия с API сервера.
- **/network/RetrofitClient.kt**: Реализация клиента для подключения к серверу через Retrofit.
- **/ui/theme/**: Глобальные стили для приложения, чтобы обеспечить единообразный внешний вид.

## 🔧 Установка

1. Склонируйте репозиторий:
    ```sh
    git clone https://github.com/Romanafanasyev/invest-sandbox-2.git
    ```
2. Установите зависимости через Gradle::
    ```sh
    ./gradlew build
    ```

## 🚀 Использование
- **Авторизация и регистрация**: Позволяет пользователям создать учетную запись и войти в приложение.
- **Просмотр списка акций**: Пользователи могут увидеть доступные акции на виртуальном рынке.
- **Покупка и продажа акций**: Легкий процесс для выполнения торговых операций.
- **Обновление цен акций в реальном времени**: Цены акций обновляются каждые 15 минут.
- **Интуитивно понятный интерфейс**: Простой и понятный интерфейс для начинающих инвесторов.

## ✅ Тестирование

- **Модульное тестирование**: Использование JUnit для тестирования отдельных компонентов приложения.
- **Интеграционное тестирование**: Проверка взаимодействия с сервером через API.
- **Ручное тестирование**: Включает тестирование UI и функциональности, таких как покупка/продажа акций, обновление цен и обработка ошибок.

## 🔍 Зависимости
- **Kotlin**: Язык программирования для разработки под Android.
- **Jetpack Compose**: Инструментарий UI для создания современных интерфейсов Android.
- **Retrofit**: HTTP-клиент для взаимодействия с API.

## 🌐 Связанный проект
Для серверной части ознакомьтесь с [Invest Sandbox - Backend](https://github.com/Romanafanasyev/invest-sandbox-2-backend).






