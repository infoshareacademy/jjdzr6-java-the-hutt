package com.infoshareacademy;

import com.infoshareacademy.fridge.Fridge;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        System.out.println("Team name: Java The Hutt");

        RecipeService recipeService = new RecipeService();
        try {

            List<Recipe> recipe = recipeService.getJson();
            recipeService.showAll(recipe);
            recipeService.findRecipeByName(recipe);
            recipeService.findRecipeByTime(recipe);
        } catch (IOException e) {
            System.out.println("IO");
        }
    }
}
