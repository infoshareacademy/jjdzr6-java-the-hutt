package com.infoshareacademy;

import com.infoshareacademy.food_preferences.FoodPreferences;
import com.infoshareacademy.fridge.Fridge;
import com.infoshareacademy.recipe.Recipe;
import com.infoshareacademy.service.FoodPreferencesService;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.RecipeService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {

    PrintMenu printMenu = new PrintMenu();
    Scanner scanner = new Scanner(System.in);
    private boolean flagMenu = true;

    public void mainMenu() throws IOException {

        int chooseMainNumber;
        do {
            printMenu.printMenu();
            try {
                chooseMainNumber = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("");
                scanner.nextLine();
                continue;
            }

            switch (chooseMainNumber) {
                case 1:
                    printMenu.goTo1Menu();
                    int chooseFunction = scanner.nextInt();
                    FoodPreferencesService foodPreferencesService = new FoodPreferencesService();
                    switch (chooseFunction) {
                        case 0:
                            continue;
                        case 1:
                            FoodPreferences foodPreferences = foodPreferencesService.getJson();
                            System.out.println(foodPreferences);
                            break;
                        case 2:

                            foodPreferencesService.writeFoodPreferencesToJson();
                            break;
                    }
                    break;
                case 2:
                    printMenu.goTo2Menu();
                    chooseFunction = scanner.nextInt();
                    FridgeService fridgeService = new FridgeService();

                    switch (chooseFunction) {
                        case 0:
                            continue;
                        case 1:
                            fridgeService.showAllProductsInFridge(fridgeService.getJson());
                            break;
                        case 2:
                            Fridge fridge = fridgeService.addProductToFridge();
                            Map<String, Double> json = fridgeService.getJson();
                            json.putAll(fridge.getProductsInFridge());
                            fridgeService.writeJson(json);
                            break;
                        case 3:
                            Fridge fridgeRemove = fridgeService.removeProductFromFridge();
                            fridgeService.writeJson(fridgeRemove.getProductsInFridge());
                            break;
                    }
                    break;
                case 3:
                    printMenu.goTo3Menu();
                    chooseFunction = scanner.nextInt();
                    RecipeService recipeService = new RecipeService();
                    switch (chooseFunction) {
                        case 0:
                            continue;
                        case 1:
                            recipeService.showAllRecipes(recipeService.getJson());
                            break;
                        case 2:
                            printMenu.goTo3AMenu();
                            int chooseAnotherFunction = scanner.nextInt();
                            switch (chooseAnotherFunction) {
                                case 0:
                                    continue;
                                case 1:
                                    recipeService.findRecipeByName(recipeService.getJson());
                                    break;
                                case 2:
                                    recipeService.findRecipeByTime(recipeService.getJson());
                                    break;
                            }
                            break;
                        case 3:
                            Recipe recipe = recipeService.addRecipe();
                            List<Recipe> jsonRecipe = recipeService.getJson();
                            jsonRecipe.add(recipe);
                            System.out.println("Dodano przepis: " + jsonRecipe);
                            recipeService.writeJson(jsonRecipe);
                            break;
                        case 4:
                            recipeService.removeRecipe();
                            break;

                    }
                    break;
                case 0:
                    printMenu.goTo0Menu();
                    scanner = new Scanner(System.in);
                    String chooseExit = scanner.nextLine();
                    if (chooseExit.toUpperCase().equals("T")) {
                        flagMenu = false;
                    } else if (chooseExit.toUpperCase().equals("N")) {
                        continue;
                    }
                    break;
                default:
                    System.out.println("Niepoprawny numer. Wybierz opcje 0-3.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
            }
        } while (flagMenu);
    }
}

