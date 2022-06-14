package com.infoshareacademy.recipe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.infoshareacademy.fridge.Fridge;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecipeMethods {

    public void writeJson(List<Recipe> recipe) throws IOException {
        Path path = Path.of("src", "main", "java", "com",
                "infoshareacademy", "recipe", "recipe.json");
        File file = new File(path.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(file, recipe);
    }

    public List<Recipe> getJson() throws IOException {
        Path path = Path.of("src", "main", "java", "com",
                "infoshareacademy", "recipe", "recipe.json");
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(path.toString());
        List<Recipe> recipe = objectMapper.readValue(file, new TypeReference<List<Recipe>>() {
        });
        return recipe;
    }

    public void showAll(List<Recipe> recipe) {
        for (int i = 0; i < recipe.size(); i++) {
            System.out.println(recipe.get(i).toString());

        }
    }

    public void findRecipeByName(List<Recipe> recipe) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Szukam przepisu na: \n");
        String search = scanner.nextLine();
        List<Recipe> findRecipe = recipe.stream().filter(list -> list.getName().equals(search)).collect(Collectors.toList());
        System.out.println(findRecipe.toString());

    }
    public void findRecipeByTime(List<Recipe> recipe) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Szukam przepisu który przygotuje w maksymalnie:   [min] \n");
        Double search = scanner.nextDouble();
        List<Recipe> findRecipe = recipe.stream().filter(list -> list.getPreparationTime()<=(search)).collect(Collectors.toList());
        System.out.println(findRecipe.toString());

    }

    public void findRecipeByProducts(List<Recipe> recipe, List<Fridge> fridge) {


        System.out.println("Szukam przepisu który przygotuje z tego co mam w lodówce: ");
        List<Recipe> findRecipeByProducts = recipe.stream()
                .filter(a -> fridge.stream()
                        .anyMatch(b -> b.getProductInFridge().keySet().equals(a.getNeccesaryProducts().keySet()))).collect(Collectors.toList());

        System.out.println(findRecipeByProducts.toString());

    }


}

