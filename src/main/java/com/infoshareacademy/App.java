package com.infoshareacademy;
import com.infoshareacademy.fridge.Fridge;
import com.infoshareacademy.service.FridgeService;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Team name: Java The Hutt");


        RecipeService recipeService = new RecipeService();
        try {
            List<Recipe> recipe = recipeService.getJson();
            recipeService.showAllRecipes(recipe);

        } catch (IOException e) {
            System.out.println("IO");
        }

        FridgeService fridgeService = new FridgeService();
        Fridge fridge = fridgeService.addProductToFridge();
        Map<String, Double> json = fridgeService.getJson();
        json.putAll(fridge.getProductsInFridge());
        System.out.println(json);
        fridge.setProductsInFridge(json);
        fridgeService.writeJson(fridge.getProductsInFridge());
    }
}
