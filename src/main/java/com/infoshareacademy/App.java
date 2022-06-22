package com.infoshareacademy;

import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;

import java.io.IOException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("Team name: Java The Hutt");

        RecipeService recipeService = new RecipeService();
        try {
            List<Recipe> recipe = recipeService.getJson();
            recipeService.showAllRecipes(recipe);

        } catch (IOException e) {
            System.out.println("IO");
        }





    }
}
