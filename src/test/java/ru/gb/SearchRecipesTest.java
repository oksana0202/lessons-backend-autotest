package ru.gb;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.gb.extensions.SpoonApiTest;


@SpoonApiTest
public class SearchRecipesTest {
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeAll
    static void beforeAll() {
        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("offset", 350)
                .addQueryParam("number", 10)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectBody("offset", Matchers.equalTo(350))
                .build();
    }

    @Test
    public void searchRecipesTest() {
        given()
                .spec(requestSpecification)
                .get("/recipes/complexSearch")
                .prettyPeek()
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .body("totalResults", Matchers.equalTo(5226));

    }
}
