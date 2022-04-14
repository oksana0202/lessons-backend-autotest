package ru.gb;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import ru.gb.extensions.SpoonApiTest;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class RecipesCuisineTest {

    @Test
     public void recipesCuisineTest(){
        String queryParameter = "Mediterranean";
         given()
                 .queryParam("query", queryParameter)
                 .queryParam("confidence", 0)
                 .queryParam("status", "OK")
                 .post("/recipes/cuisine")
                 .prettyPeek()
                 .then()
                 .statusCode(200)
                 .body("cuisine", Matchers.containsStringIgnoringCase(queryParameter));

     }

}
