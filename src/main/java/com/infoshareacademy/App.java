package com.infoshareacademy;

import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Team name: Java The Hutt");

        RecipeService recipeService = new RecipeService();
        Recipe recipe = recipeService.addRecipe();

        List<Recipe> jsonRecipe = recipeService.getJson();
        jsonRecipe.add(recipe);
        System.out.println(jsonRecipe);

        recipeService.writeJson(jsonRecipe);

    }
}
