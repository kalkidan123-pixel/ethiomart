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

| Service | Port | DB | Role |
|---------|------|-----|------|
| auth-service | 8081 | H2 `authdb` | Register, login (JWT), publish `user.registered` |
| order-service | 8082 | H2 `orderdb` | Create orders (JWT), publish `order.created` |
| payment-service | 8083 | H2 `paymentdb` | Mock payment on `order.created` |
| inventory-service | 8084 | H2 `inventorydb` | Stock check/reserve on `order.created` |
| shipping-service | 8085 | H2 `shippingdb` | Shipment when payment + stock succeed |
| notification-service | 8086 | — | Logs all domain events |

## Quick start

### 1. Start RabbitMQ

```bash
docker compose up rabbitmq -d
```

Management UI: http://localhost:15672 (guest / guest)

### 2. Build all modules

```bash
mvn clean install -DskipTests
```

### 3. Run services (separate terminals)

```bash
mvn -pl auth-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl payment-service spring-boot:run
mvn -pl inventory-service spring-boot:run
mvn -pl shipping-service spring-boot:run
mvn -pl notification-service spring-boot:run
```

### 4. Full stack with Docker

```bash
docker compose up --build
```

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

## Bonus features included

- JWT validation on Order service (shared `JWT_SECRET`)
- Dead-letter queues on consumer bindings
- Docker Compose for RabbitMQ + all services
- Springdoc OpenAPI per service

## Products & pricing (Order domain)

| Product ID | Unit price | Initial stock |
|------------|------------|---------------|
| PROD-001 | 100.00 ETB | 100 |
| PROD-002 | 250.00 ETB | 5 |
