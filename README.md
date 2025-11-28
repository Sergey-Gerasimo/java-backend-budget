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

## Пример `docker-compose.yaml`

```yaml 
version: '3.8'

services:
  backend:
    image: openjdk:17-jdk-slim
    container_name: family-budget-backend
    working_dir: /app
    volumes:
      - ./target/java-backend-1.0.0.jar:/app/app.jar
      - ./logs:/app/logs
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
      - SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/family_budget
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=password
      - SPRING_JPA_HIBERNATE_DDL_AUTO=update
      - SERVER_PORT=8080
      - SERVER_SERVLET_CONTEXT_PATH=/api
    depends_on:
      - postgres
    healthcheck:
      test: ["CMD", "curl", "-f", "http://localhost:8080/api/health"]
      interval: 30s
      timeout: 10s
      retries: 3
      start_period: 40s
    restart: unless-stopped
    command: java -jar app.jar

  postgres:
    image: postgres:15-alpine
    container_name: family-budget-postgres
    environment:
      - POSTGRES_DB=family_budget
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 30s
      timeout: 10s
      retries: 3
    restart: unless-stopped

volumes:
  postgres_data:

networks:
  default:
    name: family-budget-network
```