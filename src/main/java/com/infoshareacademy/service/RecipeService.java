package com.infoshareacademy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.Json;
import com.infoshareacademy.recipe.Recipe;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RecipeService {

    public void writeJson(List<Recipe> recipe) throws IOException {
        Json.writeJson(recipe,"recipe.json");
    }

    public List<Recipe> getJson() throws IOException {
        Path path = Path.of("src", "resources", "recipe.json");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path.toString());
        List<Recipe> recipe = objectMapper.readValue(file, new TypeReference<List<Recipe>>() {
        });
        return recipe;
    }

    public void showAllRecipes(List<Recipe> recipe) {
        recipe.forEach(oneRecipe -> System.out.println(oneRecipe));
    }

    public void findRecipeByName(List<Recipe> recipe) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Szukam przepisu na: \n");
        String search = scanner.nextLine();
        List<Recipe> findRecipe = recipe.stream().filter(list -> list.getName().equalsIgnoreCase(search)).collect(Collectors.toList());
        System.out.println(findRecipe.toString());

    }

    public void findRecipeByTime(List<Recipe> recipe) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Szukam przepisu który przygotuje w maksymalnie:   [min] \n");
        Double search = scanner.nextDouble();
        List<Recipe> findRecipe = recipe.stream().filter(list -> list.getPreparationTime() <= (search)).collect(Collectors.toList());
        System.out.println(findRecipe.toString());

    }
}

