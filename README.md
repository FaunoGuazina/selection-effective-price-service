# Effective Price Service

Spring Boot 3.5.10 · Java 21 · Hexagonal Architecture · H2 · JPA · MapStruct

Service that determines the effective product price for a given brand and application date using
priority-based selection rules.

---

# 🚀 Running the Application

## Option 1 — Docker (recommended)

Build and run:

```bash
docker compose up --build
```

Application available at:

http://localhost:8080

Stop:

```bash
docker compose down
```

## Option 1.1 — Using Makefile (Docker shortcut)

If you prefer shorter commands:

```bash
make up
```

Stop:

```bash
make down
```

Build only:

```bash
make build
```

Run tests:

```bash
make test
```

---

## Option 2 — Maven (local execution)

Build:

```bash
mvn clean package
```

Run:

```bash
mvn spring-boot:run
```

---

## Run tests

```bash
mvn test
```

---

# 📡 API

## GET `/api/prices/effective`

### Query Parameters

| Parameter           | Type                             | Required |
|---------------------|----------------------------------|----------|
| brandId             | Long                             | Yes      |
| productId           | Long                             | Yes      |
| applicationDateTime | ISO-8601 (yyyy-MM-dd'T'HH:mm:ss) | Yes      |

Example:

GET /api/prices/effective?brandId=1&productId=35455&applicationDateTime=2020-06-14T16:00:00

### Response (200)

```json
{
  "brandId": 1,
  "productId": 35455,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
```

### Error Handling

Uses RFC 7807 `ProblemDetail`.

- 400 — validation errors
- 404 — no effective price found

---

# 🧠 Business Rule

A price is considered effective when:

startDate <= applicationDateTime <= endDate

If multiple prices match:

1. Highest `priority` wins
2. If equal, highest `priceList` is selected (deterministic tie-breaker)

The effective price selection is delegated to the persistence layer using a deterministic database
query:

- startDate <= applicationDateTime <= endDate (inclusive)
- ORDER BY priority DESC, priceList DESC
- LIMIT 1

This approach ensures scalability and avoids loading the full price history into memory.

---

# 🧱 Architecture

Single-module Maven project organized by layers:

domain/
application/
adapters/
config/

## Domain

- `Price` (immutable record + invariants)
- Domain exception

No Spring dependencies.

## Application

- `GetEffectivePriceUseCase`
- `PriceRepositoryPort`

Encapsulates business orchestration.

## Adapters

### Persistence

- JPA + H2
- MapStruct mapper
- Repository adapter implementing port

### Web

- REST controller
- DTO mapping
- Global exception handling
- OpenAPI documentation

Strict dependency direction:

adapters → application → domain

Never the opposite.

---

# 🧪 Testing Strategy

### Domain

- Domain invariants validation (non-null fields, date consistency)
- Constructor-level validation (startDate <= endDate)

### Application

- Correct repository interaction
- Correct exception behavior

### Persistence

- H2 integration test
- Entity → domain mapping validation

### Web

- Integration tests with MockMvc
- Parameterized scenarios
- Validation error tests
- Not-found behavior

Test coverage validates algorithm, orchestration, and endpoint behavior.

---

# 🗄 Database

H2 in-memory database.

Initialized via:

- `schema.sql`
- `data.sql`

Seed data reflects official scenario cases.

---

# 📘 OpenAPI / Swagger

The project integrates **springdoc-openapi**.

Once the application is running, documentation is available at:

Swagger UI:

http://localhost:8080/swagger-ui.html

OpenAPI JSON:

http://localhost:8080/v3/api-docs

The controller is fully documented using OpenAPI annotations and provides:

- Endpoint description
- Parameter documentation
- Example responses
- Error response schemas (ProblemDetail)

---

# 🔍 Design Decisions

- Hexagonal architecture to isolate domain logic
- Deterministic database-driven selection (priority DESC, priceList DESC)
- Delegation of filtering and ordering to the database for scalability
- Immutable domain model
- MapStruct for explicit mapping
- ProblemDetail for standardized error responses
- Conventional Commits per incremental story step
- Small commits reflecting architectural evolution

---

# 🧾 Commit History Narrative

The project was developed incrementally following TDD principles and hexagonal layering.

## 1. Bootstrap

- Scaffold Spring Boot application
- Establish base project structure

## 2. Domain First

- Model `Price` aggregate
- Implement baseline effective price algorithm
- Add unit tests for selection rules
- Improve null-safety and boundary validation
- Introduce deterministic tie-breaker

The domain was completed and fully tested before introducing infrastructure.

## 3. Application Layer

- Introduce repository port
- Implement `GetEffectivePriceUseCase`
- Add application-level tests using stub repositories
- Replace generic exceptions with domain-specific exception

## 4. Persistence Adapter

- Configure H2 schema and seed data
- Implement JPA entity
- Add MapStruct mapper
- Implement repository adapter
- Add integration test

## 5. Web Adapter

- Implement REST controller
- Add response DTO
- Add integration tests
- Centralize exception handling with `GlobalControllerAdvice`
- Improve validation annotations

## 6. Documentation & DevOps

- Add OpenAPI documentation
- Add Docker support
- Add Makefile
- Improve configuration clarity

Each commit reflects a clear architectural step and respects Conventional Commits.

---

# 🛠 Tech Stack

- Java 21
- Spring Boot 3.5.10
- Spring Web
- Spring Data JPA
- H2
- MapStruct
- Lombok
- springdoc-openapi
- JUnit 5
- AssertJ
- MockMvc
- Docker