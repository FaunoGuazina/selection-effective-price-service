.PHONY: build test up down logs clean

build:
	mvn clean package

test:
	mvn test

up:
	docker compose up --build

down:
	docker compose down

logs:
	docker compose logs -f

clean:
	docker compose down -v