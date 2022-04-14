package ru.gb;

import static io.restassured.RestAssured.given;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;

@SpoonApiTest
public class SearchRecipesTest {

    @Test
    public void searchRecipesTest() {
        given()
                .queryParam("offset", 350)
                .queryParam("number", 10)
                .get("/recipes/complexSearch")
                .then()
                .statusCode(200)
                .body("totalResults", Matchers.equalTo(5222));
    }
}
