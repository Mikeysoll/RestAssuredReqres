import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class ReqresAPITests extends TestBase {

    @Test
    public void listUsers() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")

                .when()
                .get("/users?page=2")

                .then()
                .log().body()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()));
    }

    @Test
    public void createUser() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{ \"name\": \"mikey\", \"job\": \"aqa\" }")

                .when()
                .post("/users")

                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo("mikey"))
                .body("job", equalTo("aqa"))
                .body("id", not(empty()));
    }

    @Test
    public void getUserById() {

        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{ \"name\": \"mikey\", \"job\": \"devOps\" }")

                .when()
                .patch("/users/2")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("mikey"))
                .body("job", equalTo("devOps"))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void userNotFound() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")

                .when()
                .get("/users/999")

                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void registrationSuccessful() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }")

                .when()
                .post("/register")

                .then()
                .log().body()
                .statusCode(200)
                .body("id", not(empty()))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }
}
