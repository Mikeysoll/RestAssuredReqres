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
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .filter(withCustomTemplates());
    }
}