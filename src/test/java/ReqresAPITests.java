import models.*;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReqresAPITests extends TestBase {

    @Test
    public void listUsersTest() {
        step("Send GET request to list users on page 2 and verify response", () -> {
            ListUsersResponse response = baseRequest()
                    .when()
                    .get("/users?page=2")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .extract().as(ListUsersResponse.class);

            assertEquals(2, response.getPage());
            assertFalse(response.getData().isEmpty());
        });
    }

    @Test
    public void createUserTest() {
        step("Create a new user and verify response", () -> {
            UserDTO user = new UserDTO("mikey", "AQA");
            UserDTO responseUser = baseRequest()
                    .body(user)
                    .when()
                    .post("/users")
                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().as(UserDTO.class);
            assertEquals(user.getName(), responseUser.getName());
            assertEquals(user.getJob(), responseUser.getJob());
            assertNotNull(responseUser.getId());
            assertNotNull(responseUser.getCreatedAt());
        });
    }

    @Test
    public void updateUserTest() {
        step("Update user with ID 2 and verify response", () -> {
            UserDTO user = new UserDTO("mikey", "devOps");
            UserUpdateResponse response = baseRequest()
                    .body(user)
                    .when()
                    .put("/users/2")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .extract().as(UserUpdateResponse.class);
            assertEquals(user.getName(), response.getName());
            assertEquals(user.getJob(), response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }

    @Test
    public void userNotFoundTest() {
        step("Request non-existing user and verify 404 response", () -> {
            baseRequest()
                    .when()
                    .get("/users/9999")
                    .then()
                    .log().body()
                    .statusCode(404);
        });
    }

    @Test
    public void registrationSuccessfulTest() {
        step("Register a new user and verify response", () -> {
            AuthDTO auth = new AuthDTO("eve.holt@reqres.in", "pistol");
            AuthResponse response = baseRequest()
                    .body(auth)
                    .when()
                    .post("/register")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .extract().as(AuthResponse.class);
            assertNotNull(response.getId());
            assertNotNull(response.getToken());
        });
    }

    @Test
    public void partialUpdateUserTest() {
        step("Partially update user with ID 2 and verify response", () -> {
            UserDTO user = new UserDTO("morpheus", "zion resident");
            UserUpdateResponse response = baseRequest()
                    .body(user)
                    .when()
                    .patch("/users/2")
                    .then()
                    .log().body()
                    .statusCode(200)
                    .extract().as(UserUpdateResponse.class);
            assertEquals(user.getName(), response.getName());
            assertEquals(user.getJob(), response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }

    @Test
    public void deleteUserTest() {
        step("Delete user with ID 2 and verify response", () -> {
            baseRequest()
                    .when()
                    .delete("/users/2")
                    .then()
                    .log().body()
                    .statusCode(204);
        });
    }

    @Test
    void successfulLoginTest() {
        step("Login user and verify token", () -> {
            AuthDTO auth = new AuthDTO("eve.holt@reqres.in", "cityslicka");
            AuthResponse response = baseRequest()
                    .body(auth)
                    .when()
                    .post("/login")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(AuthResponse.class);
            assertNotNull(response.getToken());
        });
    }
}
