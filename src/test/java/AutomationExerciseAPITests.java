import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class AutomationExerciseAPITests extends TestBase {

    @Test
    public void getAllProductsList() {
        given()
                .log().uri()

                .when()
                .get("/productsList")

                .then()
                .log().body()
                .statusCode(200)
                .body("products", notNullValue());

    }

}
