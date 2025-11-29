import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class ReqresAPITests extends TestBase {

    @Test
    public void listUsersTest() {
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
    public void createUserTest() {
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
    public void updateUserTest() {

        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{ \"name\": \"mikey\", \"job\": \"devOps\" }")

                .when()
                .put("/users/2")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("mikey"))
                .body("job", equalTo("devOps"))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void userNotFoundTest() {
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")

                .when()
                .get("/users/9999")

                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void registrationSuccessfulTest() {
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

    @Test
    public void partialUpdateUserTest(){
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")

                .when()
                .patch("/users/2")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void deleteUserTest(){
        given()
                .log().uri()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")

                .when()
                .delete("/users/2")

                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    void successfulLoginTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";

        given()
                .body(authData)
                .header("x-api-key", "reqres-free-v1")
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
