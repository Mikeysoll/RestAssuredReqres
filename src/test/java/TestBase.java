import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import io.restassured.specification.RequestSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class TestBase {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    protected RequestSpecification baseRequest() {
        return given()
                .header("x-api-key", "reqres_f9e34898826a4f4682a2d39ec238602a")
                .contentType(ContentType.JSON)
                .filter(withCustomTemplates());
    }
}