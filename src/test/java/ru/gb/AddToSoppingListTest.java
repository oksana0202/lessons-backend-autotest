package ru.gb;


import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.gb.dto.AddItemToShoppingListRequest;
import ru.gb.dto.CreateUserRequest;
import ru.gb.dto.CreateUserResponse;
import ru.gb.endpoints.SpoonEndpoints;
import ru.gb.extensions.SpoonApiTest;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;

@SpoonApiTest
public class AddToSoppingListTest {
    private static CreateUserResponse createUserResponse;
    private static RequestSpecification hashParam;
    private int id;

    @BeforeAll
    static void beforeAll() {
        Faker faker = new Faker();
        createUserResponse = given()
                .body(CreateUserRequest.builder()
                                .username(faker.funnyName().name())
                                .firstName(faker.name().firstName())
                                .lastName(faker.name().lastName())
                                .email(faker.internet().emailAddress())
                                .build())
                .post(SpoonEndpoints.USER_CONNECT.getEndpoint())
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .as(CreateUserResponse.class);
        hashParam = new RequestSpecBuilder()
                .addQueryParam("hash",createUserResponse.getHash())
                .build();
    }


    @BeforeEach
    void setUp(){
        given()
                .spec(hashParam)
                .get(SpoonEndpoints.MEALPLANNER_USERNAME_SHOPPING_LIST.getEndpoint(), createUserResponse.getUsername())
                .then()
                .statusCode(200);
    }
    public static Stream<AddItemToShoppingListRequest> shoppingListRequests() {
        return Stream.of(AddItemToShoppingListRequest.builder()
                        .item("1 kg cucumbers")
                        .aisle("Cucumber")
                        .parse(true)
                .build(),
                AddItemToShoppingListRequest.builder()
                        .item("2 kg tomatos")
                        .aisle("Tomato")
                        .parse(true)
                        .build());
    }

    @ParameterizedTest
    @MethodSource("shoppingListRequests")
    void AddToSoppingListTest(AddItemToShoppingListRequest addItemToShoppingListRequest){

        given()
                .log()
                .all()
                .spec(hashParam)
                .body(addItemToShoppingListRequest)
                .post(SpoonEndpoints.MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS.getEndpoint(), createUserResponse.getUsername())
                .then()
                .statusCode(200);

        id = given()
                .spec(hashParam)
                .get(SpoonEndpoints.MEALPLANNER_USERNAME_SHOPPING_LIST.getEndpoint(), createUserResponse.getUsername())
                .then()
                .statusCode(200)
                .body("aisles.aisle", Matchers.hasItems(addItemToShoppingListRequest.getAisle()))
                .extract()
                .jsonPath()
                .getInt("aisles.items[0].id[0]");
    }
    @AfterEach
    void tearDown(){
        given()
                .spec(hashParam)
                .delete(SpoonEndpoints.MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS_ID.getEndpoint(), createUserResponse.getUsername(), id)
                .then()
                .statusCode(200);
    }
}
