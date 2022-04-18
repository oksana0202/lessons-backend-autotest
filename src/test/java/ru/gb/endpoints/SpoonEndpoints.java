package ru.gb.endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SpoonEndpoints {
    FOOD_SEARCH("/recipes/complexSearch"),
    USER_CONNECT("/users/connect"),
    MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS("/mealplanner/{username}/shopping-list/items"),
    MEALPLANNER_USERNAME_SHOPPING_LIST_ITEMS_ID("/mealplanner/{username}/shopping-list/items/{id}"),
    MEALPLANNER_USERNAME_SHOPPING_LIST("/mealplanner/{username}/shopping-list");

    @Getter
    final String endpoint;
}
