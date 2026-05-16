# EthioMart — Event-Driven Microservices

Six independent Spring Boot services communicating **only via RabbitMQ** (no service-to-service REST), each following **Onion Architecture**.

## Architecture

```
┌─────────────┐     user.registered      ┌──────────────────────┐
│ Auth :8081  │ ───────────────────────► │ Notification :8086   │
└─────────────┘                          └──────────────────────┘
       ▲                                           ▲
       │ login/register (REST)                     │ all events
       │                                           │
┌─────────────┐     order.created        ┌─────────┴────────────┐
│ Order :8082 │ ───────────┬────────────►│ Payment :8083        │
└─────────────┘            │             │ Inventory :8084      │
                           │             └──────────┬───────────┘
                           │                        │
                           │    payment.completed   │ stock.reserved
                           │         stock.*        │
                           └──────────►┌────────────▼───────────┐
                                       │ Shipping :8085         │
                                       │ (waits for BOTH)       │
                                       └──────────┬─────────────┘
                                                  │ shipment.created
                                                  ▼
                                       ┌──────────────────────┐
                                       │ Notification :8086   │
                                       └──────────────────────┘
```

### Onion layers (every service)

| Layer | Responsibility |
|-------|----------------|
| **Domain** | Entities, business rules — zero Spring/framework |
| **Application** | Use cases, input/output ports |
| **Infrastructure** | JPA, RabbitMQ, JWT, adapters |
| **Presentation** | REST controllers, DTOs |

### RabbitMQ

- **Exchange:** `app.exchange` (topic)
- **DLX:** `app.exchange.dlx`
- **Routing keys:** `user.registered`, `order.created`, `payment.completed`, `payment.failed`, `stock.reserved`, `stock.failed`, `shipment.created`

## Services

| Service | Port | DB (local / Docker) | Role |
|---------|------|------------------------|------|
| auth-service | 8081 | H2 / PostgreSQL `authdb` | Register, login (JWT), publish `user.registered` |
| order-service | 8082 | H2 / PostgreSQL `orderdb` | Create orders (JWT), publish `order.created` |
| payment-service | 8083 | H2 / PostgreSQL `paymentdb` | Mock payment on `order.created` |
| inventory-service | 8084 | H2 / PostgreSQL `inventorydb` | Stock check/reserve on `order.created` |
| shipping-service | 8085 | H2 / PostgreSQL `shippingdb` | Shipment when payment + stock succeed |
| notification-service | 8086 | — | Logs all domain events |

## Quick start

### Option A — Full stack with Docker (recommended)

Starts **PostgreSQL**, **RabbitMQ**, and all six services:

```bash
docker compose up --build
```

- Postgres: `localhost:5432` (user/pass: `ethiomart` / `ethiomart`)
- RabbitMQ UI: http://localhost:15672 (guest / guest)

### Option B — Local dev (H2 + RabbitMQ only)

```bash
docker compose up rabbitmq -d
mvn clean install -DskipE2e=true
# Run each service in its own terminal:
mvn -pl auth-service spring-boot:run
mvn -pl order-service spring-boot:run
# ... payment, inventory, shipping, notification
```

Use `SPRING_PROFILES_ACTIVE=docker` plus Postgres running if you want PostgreSQL locally without full Compose.

## API walkthrough

### Register & login (Auth)

```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"user@ethio.com\",\"fullName\":\"Abebe\",\"password\":\"secret12\"}"

curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d "{\"email\":\"user@ethio.com\",\"password\":\"secret12\"}"
```

Save the `accessToken` from the login response.

### Create order (Order — JWT required)

```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <accessToken>" \
  -d "{\"productId\":\"PROD-001\",\"quantity\":2}"
```

### Query downstream state

```bash
curl http://localhost:8083/api/payments/{orderId}
curl http://localhost:8084/api/inventory/PROD-001
curl http://localhost:8085/api/shipments/{orderId}
```

Watch **notification-service** console for `NOTIFICATION =>` lines for every event.

## Swagger

| Service | UI |
|---------|-----|
| Auth | http://localhost:8081/swagger-ui.html |
| Order | http://localhost:8082/swagger-ui.html |
| Payment | http://localhost:8083/swagger-ui.html |
| Inventory | http://localhost:8084/swagger-ui.html |
| Shipping | http://localhost:8085/swagger-ui.html |
| Notification | http://localhost:8086/swagger-ui.html |

## CI and E2E tests

```bash
# Full build + Testcontainers E2E (requires Docker)
mvn verify

# Skip E2E (faster local compile)
mvn clean install -DskipE2e=true
```

GitHub Actions runs `mvn verify` on every push (see `.github/workflows/ci.yml`).

E2E tests (`e2e-tests` module) start `docker-compose.e2e.yml` (no fixed host ports) and assert register → login → order → payment → shipment.

> If `docker compose up` is already running, stop it before `mvn verify` to avoid port conflicts on 5432/5672/808x.

Manual script: `scripts/verify-e2e.ps1`

## Bonus features included

- JWT validation on Order service (shared `JWT_SECRET`)
- Dead-letter queues on consumer bindings
- PostgreSQL per service in Docker (`application-docker.yml` profile)
- Docker Compose for full stack
- Testcontainers E2E + GitHub Actions CI
- Springdoc OpenAPI per service

## Products & pricing (Order domain)

| Product ID | Unit price | Initial stock |
|------------|------------|---------------|
| PROD-001 | 100.00 ETB | 100 |
| PROD-002 | 250.00 ETB | 5 |
