package com.infoshareacademy.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infoshareacademy.food_preferences.AllergenName;
import com.infoshareacademy.food_preferences.FoodPreferences;
import com.infoshareacademy.food_preferences.Meat;
import com.infoshareacademy.recipe.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FoodPreferencesService {
    Scanner scanner = new Scanner(System.in);
    boolean run;
    public boolean preferencesFlag() {
        boolean yesPreferences = false;
        run = false;

        do {
            try{
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("T")) {
                yesPreferences = true;
                run = true;
            } else if (answer.equalsIgnoreCase("N")) {
                yesPreferences = false;
                run = true;
            } else {
                System.out.println("Błędny format odpowiedzi. Wprowadź odpowiedź ponownie.");
                }
            } catch (InputMismatchException exception){
                System.out.println("Błędny format odpowiedzi. Wprowadź odpowiedź ponownie.");
            }
        } while (!run);
        return yesPreferences;
    }

    public AllergenName setAllergenPreferences() {
        AllergenName allergenName = new AllergenName();


        System.out.println("Czekolada[T/N]:");
        allergenName.setChocolate(preferencesFlag());
        System.out.println("Orzechy[T/N]:");
        allergenName.setNuts(preferencesFlag());
        System.out.println("Jajka[T/N]:");
        allergenName.setEggs(preferencesFlag());
        System.out.println("Truskawki[T/N]:");
        allergenName.setStrawberries(preferencesFlag());
        System.out.println("Produkty mleczne[T/N]:");
        allergenName.setDairy(preferencesFlag());
        System.out.println("Inne alergie (podaj): ");
        allergenName.setOther(scanner.nextLine());

        System.out.println(allergenName);
        return allergenName;
    }

    public Meat setMeatPreferences() {
        Meat meat = new Meat();

        System.out.println("Mięso[T/N]: ");
        meat.setMeatEater(preferencesFlag());
        System.out.println("Dieta Wegańska[T/N]: ");
        meat.setVegan(preferencesFlag());
        System.out.println("Dieta Wegetariańska[T/N]: ");
        meat.setVegetarian(preferencesFlag());

        System.out.println(meat);
        return meat;
    }

    public void writeFoodPreferencesToJson() throws IOException {
        Path path = Path.of("src", "resources", "food_preferences.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        FoodPreferences foodPreferences = new FoodPreferences();
        AllergenName allergenName = setAllergenPreferences();
        foodPreferences.setAllergenName(allergenName);
        Meat meat = setMeatPreferences();
        foodPreferences.setMeat(meat);

        String foodPreferencesJson = objectWriter.writeValueAsString(foodPreferences);
        Files.writeString(path, foodPreferencesJson);
    }

    public FoodPreferences getJson() throws IOException {
        Path path = Path.of("src","resources","food_preferences.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        File file = new File(path.toString());
        FoodPreferences foodPreferences = objectMapper.readValue(file, new TypeReference<FoodPreferences>() {
        });
        return foodPreferences;
    }

    public void recipeListContainAllergens() throws IOException {
        RecipeService recipeService = new RecipeService();
        List<Recipe> recipeList = recipeService.getJson();

        FoodPreferencesService foodPreferencesService = new FoodPreferencesService();
        FoodPreferences foodPreferencesJson = foodPreferencesService.getJson();

        System.out.println(foodPreferencesJson.getAllergenName().getOther());
        if (foodPreferencesJson.getAllergenName().isChocolate()) {
            List<Recipe> chocolate = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains("czekolada"))
                    .collect(Collectors.toList());
            recipeList = chocolate;
        }
        if (foodPreferencesJson.getAllergenName().isNuts()) {
            List<Recipe> nuts = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains("orzechy"))
                    .collect(Collectors.toList());
            recipeList = nuts;
        }
        if (foodPreferencesJson.getAllergenName().isEggs()) {
            List<Recipe> eggs = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains("jajka"))
                    .collect(Collectors.toList());
            recipeList = eggs;
        }
        if (foodPreferencesJson.getAllergenName().isStrawberries()) {
            List<Recipe> strawberries = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains("truskawki"))
                    .collect(Collectors.toList());
            recipeList = strawberries;
        }
        if (foodPreferencesJson.getAllergenName().isDairy()) {
            List<Recipe> diary = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains("mleko"))
                    .collect(Collectors.toList());
            recipeList = diary;
        }
        if (!foodPreferencesJson.getAllergenName().getOther().equals("-")) {
            List<Recipe> other = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains(foodPreferencesJson.getAllergenName().getOther()))
                    .collect(Collectors.toList());
            recipeList = other;
        } else if (!foodPreferencesJson.getAllergenName().getOther().equals("brak")){
            List<Recipe> other = recipeList.stream()
                    .filter(s -> !s.getNeccesaryProducts().keySet().contains(foodPreferencesJson.getAllergenName().getOther()))
                    .collect(Collectors.toList());
            recipeList = other;
        }
        if (foodPreferencesJson.getMeat().isVegetarian()) {
            List<Recipe> vegetarian = recipeList.stream()
                    .filter(Recipe::isVegetarian)
                    .collect(Collectors.toList());
            recipeList = vegetarian;
        }
        if (foodPreferencesJson.getMeat().isVegan()) {
            List<Recipe> vegan = recipeList.stream()
                    .filter(Recipe::isVegan)
                    .collect(Collectors.toList());
            recipeList = vegan;
        }
        System.out.println(recipeList);
    }

}
