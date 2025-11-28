# Проект по ТРИС в ГУАПе

Backend-приложение для системы учета семейного бюджета, предоставляющее REST API для управления категориями трат, транзакциями, бюджетами и генерации отчетов. Приложение построено на Spring Boot 3 с использованием Java 17.

## Технологический стек
- Java 17
- Spring Boot 3.2.0
- Spring Data JPA - для работы с базой данных
- Spring Web - для REST API
- Spring Validation - для валидации данных
- PostgreSQL - реляционная база данных
- SpringDoc OpenAPI 3 - документация API
- Maven - система сборки

## Документация API
- Swagger UI: http://localhost:8080/api/docs

## Основные endpoints

### Категории
- `GET /api/v1/categories` - список всех категорий
- `POST /api/v1/categories` - создание категории
- `GET /api/v1/categories/{id}` - получение категории по ID
- `PUT /api/v1/categories/{id}` - обновление категории
- `DELETE /api/v1/categories/{id}` - удаление категории

### Транзакции
- `GET /api/v1/transactions` - список транзакций
- `POST /api/v1/transactions` - создание транзакции
- `GET /api/v1/transactions/{id}` - получение транзакции по ID
- `PUT /api/v1/transactions/{id}` - обновление транзакции
- `DELETE /api/v1/transactions/{id}` - удаление транзакции

### Бюджеты
- `GET /api/v1/budgets` - список бюджетов
- `POST /api/v1/budgets` - создание бюджета
- `GET /api/v1/budgets/{id}` - получение бюджета по ID
- `DELETE /api/v1/budgets/{id}` - удаление бюджета

### Отчеты
- `GET /api/v1/reports/summary` - сводный отчет за период
- `GET /api/v1/reports/by-category` - отчет по категориям