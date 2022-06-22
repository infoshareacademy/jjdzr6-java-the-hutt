package com.infoshareacademy;

import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.service.RecipeService;

import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Team name: Java The Hutt");

        RecipeService recipeService = new RecipeService();
        Recipe recipe = new Recipe();
        List<Recipe> jsonRecipe = recipeService.getJson();
        System.out.println(jsonRecipe);

        Scanner scanner = new Scanner(System.in);
//        System.out.println("name");
//        recipe.setName(scanner.nextLine());
//        System.out.println("desc");
//        recipe.setDescription(scanner.nextLine());
//        System.out.println("time");
//        recipe.setPreparationTime(scanner.nextInt());
        System.out.println("set How many");
        int howMany = scanner.nextInt();
        while (howMany > 0) {
            System.out.println("products name");
            scanner = new Scanner(System.in);
            String necessaryProductsName = scanner.nextLine();
            System.out.println("products amount");
            scanner = new Scanner(System.in);
            double necessaryProductsAmount = scanner.nextDouble();
            recipe.addNecessaryProducts(necessaryProductsName, necessaryProductsAmount);
            howMany--;
        }
        System.out.println("size" + recipe.getNeccesaryProducts().size());
        System.out.println(recipe.getNeccesaryProducts());
//        System.out.println(recipe.addProducts("koko", 2.0));

//        recipe.setNeccesaryProducts(recipe.addProducts("koko", 2.0));
//        System.out.println(recipe.getNeccesaryProducts());


    }
}
