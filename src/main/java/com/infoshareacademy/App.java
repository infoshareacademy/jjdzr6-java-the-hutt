package com.infoshareacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.food_preferences.FoodPreferences;
import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.service.FoodPreferencesService;
import com.infoshareacademy.service.RecipeService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Team name: Java The Hutt");


        FoodPreferencesService foodPreferencesService = new FoodPreferencesService();
        FoodPreferences json = foodPreferencesService.getJson();
//        System.out.println(json);
        foodPreferencesService.recipeListContainAllergens();

        RecipeService recipeService = new RecipeService();
        Recipe recipe = new Recipe();
        List<Recipe> recipeList = recipeService.getJson();


    }
}
