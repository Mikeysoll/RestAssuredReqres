import models.AuthDTO;
import models.UserDTO;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class ReqresAPITests extends TestBase {

    @Test
    public void listUsersTest() {
        baseRequest()
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
        UserDTO user = UserDTO.builder()
                .name("mikey")
                .job("aqa")
                .build();

        baseRequest()
                .body(user)

                .when()
                .post("/users")

                .then()
                .log().body()
                .statusCode(201)
                .body("name", equalTo(user.getName()))
                .body("job", equalTo(user.getJob()))
                .body("id", not(empty()));
    }

    @Test
    public void updateUserTest() {
        UserDTO user = UserDTO.builder()
                .name("mikey")
                .job("devOps")
                .build();

        baseRequest()
                .body(user)

                .when()
                .put("/users/2")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(user.getName()))
                .body("job", equalTo(user.getJob()))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void userNotFoundTest() {
        baseRequest()
                .when()
                .get("/users/9999")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void registrationSuccessfulTest() {
        AuthDTO auth = AuthDTO.builder()
                .email("eve.holt@reqres.in")
                .password("pistol").build();

        baseRequest()
                .body(auth)
                .when()
                .post("/register")
                .then()
                .log().body()
                .statusCode(200)
                .body("id", not(empty()))
                .body("token", not(empty()));
    }

    @Test
    public void partialUpdateUserTest() {
        UserDTO user = UserDTO.builder()
                .name("morpheus")
                .job("zion resident")
                .build();

        baseRequest()
                .body(user)
                .when()
                .patch("/users/2")
                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo(user.getName()))
                .body("updatedAt", notNullValue());
    }

    @Test
    public void deleteUserTest() {
        baseRequest()
                .when()
                .delete("/users/2")
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    void successfulLoginTest() {
        AuthDTO auth = AuthDTO.builder()
                .email("eve.holt@reqres.in")
                .password("cityslicka")
                .build();

        baseRequest()
                .body(auth)
                .when()
                .post("/login")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
