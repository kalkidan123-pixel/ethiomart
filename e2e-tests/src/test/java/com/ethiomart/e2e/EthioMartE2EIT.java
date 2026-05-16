package com.ethiomart.e2e;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

@Testcontainers
class EthioMartE2EIT {

    private static final File COMPOSE_FILE = new File(
            System.getProperty("ethiomart.root"),
            "docker-compose.e2e.yml");

    @Container
    @SuppressWarnings("resource")
    static final DockerComposeContainer<?> STACK = new DockerComposeContainer<>(COMPOSE_FILE)
            .withBuild(true)
            .withLocalCompose(true)
            .withEnv("JWT_SECRET", "ethiomart-super-secret-key-change-in-production-min-32-chars")
            .withExposedService("auth-service", 8081)
            .withExposedService("order-service", 8082)
            .withExposedService("payment-service", 8083)
            .withExposedService("shipping-service", 8085)
            .waitingFor("postgres", Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)))
            .waitingFor("rabbitmq", Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)))
            .waitingFor("auth-service", Wait.forHttp("/swagger-ui.html")
                    .withStartupTimeout(Duration.ofMinutes(5)))
            .waitingFor("order-service", Wait.forHttp("/swagger-ui.html")
                    .withStartupTimeout(Duration.ofMinutes(5)))
            .waitingFor("payment-service", Wait.forHttp("/swagger-ui.html")
                    .withStartupTimeout(Duration.ofMinutes(5)))
            .waitingFor("inventory-service", Wait.forHttp("/swagger-ui.html")
                    .withStartupTimeout(Duration.ofMinutes(5)))
            .waitingFor("shipping-service", Wait.forHttp("/swagger-ui.html")
                    .withStartupTimeout(Duration.ofMinutes(5)))
            .waitingFor("notification-service", Wait.forHttp("/api/notifications/health")
                    .withStartupTimeout(Duration.ofMinutes(5)));

    private static String authBase;
    private static String orderBase;
    private static String paymentBase;
    private static String shippingBase;

    @BeforeAll
    static void resolveBases() {
        authBase = serviceUrl("auth-service", 8081);
        orderBase = serviceUrl("order-service", 8082);
        paymentBase = serviceUrl("payment-service", 8083);
        shippingBase = serviceUrl("shipping-service", 8085);
    }

    private static String serviceUrl(String service, int port) {
        return "http://" + STACK.getServiceHost(service, port) + ":" + STACK.getServicePort(service, port);
    }

    @Test
    void registerLoginOrder_paymentAndShipmentComplete() {
        String email = "e2e-" + System.currentTimeMillis() + "@ethio.com";

        given()
                .baseUri(authBase)
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "fullName", "E2E User", "password", "secret12"))
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(201);

        String token = given()
                .baseUri(authBase)
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "password", "secret12"))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

        String orderId = given()
                .baseUri(orderBase)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(Map.of("productId", "PROD-001", "quantity", 2))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(201)
                .body("amount", equalTo(200))
                .extract()
                .path("orderId");

        await().atMost(Duration.ofSeconds(45)).pollInterval(Duration.ofSeconds(2)).until(() ->
                given().baseUri(paymentBase).when().get("/api/payments/" + orderId).then().extract().statusCode() == 200);

        given()
                .baseUri(paymentBase)
                .when()
                .get("/api/payments/" + orderId)
                .then()
                .statusCode(200)
                .body("status", equalTo("COMPLETED"));

        await().atMost(Duration.ofSeconds(45)).pollInterval(Duration.ofSeconds(2)).until(() ->
                given().baseUri(shippingBase).when().get("/api/shipments/" + orderId).then().extract().statusCode() == 200);

        given()
                .baseUri(shippingBase)
                .when()
                .get("/api/shipments/" + orderId)
                .then()
                .statusCode(200)
                .body("trackingNumber", startsWith("TRK-"));
    }

    @Test
    void createOrder_withoutToken_returns401() {
        given()
                .baseUri(orderBase)
                .contentType(ContentType.JSON)
                .body(Map.of("productId", "PROD-001", "quantity", 1))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(401);
    }

    @Test
    void outOfStockOrder_doesNotCreateShipment() {
        String email = "oos-" + System.currentTimeMillis() + "@ethio.com";

        given()
                .baseUri(authBase)
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "fullName", "OOS User", "password", "secret12"))
                .when()
                .post("/api/auth/register")
                .then()
                .statusCode(201);

        String token = given()
                .baseUri(authBase)
                .contentType(ContentType.JSON)
                .body(Map.of("email", email, "password", "secret12"))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .path("accessToken");

        String orderId = given()
                .baseUri(orderBase)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(Map.of("productId", "PROD-002", "quantity", 10))
                .when()
                .post("/api/orders")
                .then()
                .statusCode(201)
                .extract()
                .path("orderId");

        await().pollDelay(Duration.ofSeconds(8)).atMost(Duration.ofSeconds(20)).until(() -> true);

        given()
                .baseUri(shippingBase)
                .when()
                .get("/api/shipments/" + orderId)
                .then()
                .statusCode(404);
    }
}
