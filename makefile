# Variables
include .env
export

DOCKER_COMPOSE=docker-compose.yml
SERVICE_NAME=app

.PHONY: help all build run stop clean logs shell status restart

help:
	@echo "Available commands:"
	@echo "  make all        - Build and run the application"
	@echo "  make build      - Build Docker images"
	@echo "  make run        - Start application in background"
	@echo "  make up         - Start application in foreground"
	@echo "  make stop       - Stop application"
	@echo "  make clean      - Stop and remove containers, volumes, networks"
	@echo "  make logs       - Show application logs"
	@echo "  make logs-f     - Show application logs (follow mode)"
	@echo "  make shell      - Open shell in application container"
	@echo "  make status     - Show container status"
	@echo "  make restart    - Restart application"
	@echo "  make env        - Show current environment variables"

all: build run

build:
	@echo "Building Docker images for ${APP_NAME}..."
	docker-compose -f $(DOCKER_COMPOSE) build

run:
	@echo "Starting ${APP_NAME} on port ${SERVER_PORT}..."
	docker-compose -f $(DOCKER_COMPOSE) up -d

up:
	@echo "Starting ${APP_NAME} in foreground..."
	docker-compose -f $(DOCKER_COMPOSE) up

stop:
	@echo "Stopping ${APP_NAME}..."
	docker-compose -f $(DOCKER_COMPOSE) down

clean:
	@echo "Cleaning up ${APP_NAME}..."
	docker-compose -f $(DOCKER_COMPOSE) down -v --remove-orphans

logs:
	docker-compose -f $(DOCKER_COMPOSE) logs $(SERVICE_NAME)

logs-f:
	docker-compose -f $(DOCKER_COMPOSE) logs -f $(SERVICE_NAME)

shell:
	docker-compose -f $(DOCKER_COMPOSE) exec $(SERVICE_NAME) /bin/bash

status:
	docker-compose -f $(DOCKER_COMPOSE) ps

restart:
	docker-compose -f $(DOCKER_COMPOSE) restart

env:
	@echo "Current environment:"
	@echo "APP_NAME: ${APP_NAME}"
	@echo "SERVER_PORT: ${SERVER_PORT}"
	@echo "DB_HOST: ${DB_HOST}"
	@echo "DB_NAME: ${DB_NAME}"

# Database operations
db-shell:
	docker-compose -f $(DOCKER_COMPOSE) exec postgres psql -U ${DB_USER} -d ${DB_NAME}

db-logs:
	docker-compose -f $(DOCKER_COMPOSE) logs postgres

# Health checks
health:
	@curl -f http://localhost:${SERVER_PORT}${CONTEXT_PATH}health || echo "Application is not healthy"

docs:
	@echo "OpenAPI documentation available at: http://localhost:${SERVER_PORT}${CONTEXT_PATH}${SWAGGER_UI_PATH}"

prune:
	@echo "Cleaning up Docker system..."
	docker system prune -f