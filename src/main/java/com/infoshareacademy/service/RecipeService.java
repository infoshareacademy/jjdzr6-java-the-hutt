package com.infoshareacademy.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infoshareacademy.other.Json;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;


@Service
public class RecipeService {

//    public void writeJson(List<Recipe> recipe) throws IOException {
//        Json.writeJson(recipe, "recipe.json");
//    }
//
//    public List<Recipe> getJson() throws IOException {
//        Path path = Path.of("src", "resources", "recipe.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//        File file = new File(path.toString());
//        List<Recipe> recipe = objectMapper.readValue(file, new TypeReference<List<Recipe>>() {
//        });
//        return recipe;
//    }

//    // DONE
//    public void showAllRecipes(List<Recipe> recipe) {
//        recipe.forEach(oneRecipe -> System.out.println(oneRecipe));
//    }
//
//    // DONE
//    public void findRecipeByName(List<Recipe> recipe) {
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Szukam przepisu na: \n");
//        String search = scanner.nextLine();
//        List<Recipe> findRecipe = recipe.stream().filter(list -> list.getName().equalsIgnoreCase(search)).collect(Collectors.toList());
//        System.out.println(findRecipe.toString());
//    }
//
//    // DONE
//    public void findRecipeByTime(List<Recipe> recipe) {
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Szukam przepisu który przygotuje w maksymalnie:   [min] \n");
//        Double search = scanner.nextDouble();
//        List<Recipe> findRecipe = recipe.stream().filter(list -> list.getPreparationTime() <= (search)).collect(Collectors.toList());
//        System.out.println(findRecipe.toString());
//    }
//
//    public Recipe addRecipe() {
//        Recipe recipe = new Recipe();
//        Scanner scanner;
//        boolean run = false;
//
//        do {
//            try {
//                scanner = new Scanner(System.in);
//                System.out.println("Tytuł przepisu:");
//                recipe.setName(scanner.nextLine());
//                System.out.println("Krótki opis:");
//                recipe.setDescription(scanner.nextLine());
//                System.out.println("Czas przygotowania (w min.):");
//                recipe.setPreparationTime(scanner.nextInt());
//                System.out.println("Ilość niezbędnych składników (w szt.):");
//                int howMany = scanner.nextInt();
//                while (howMany > 0) {
//                    System.out.println("Nazwa składnika:");
//                    scanner = new Scanner(System.in);
//                    String necessaryProductsName = scanner.nextLine();
//                    System.out.println("Ilość składnika:");
//                    scanner = new Scanner(System.in);
//                    double necessaryProductsAmount = scanner.nextDouble();
//                    recipe.addNecessaryProducts(necessaryProductsName, necessaryProductsAmount);
//                    howMany--;
//                    run = true;
//                }
//            } catch (InputMismatchException exception) {
//                System.out.println("Niepoprawny format odpowiedzi.");
//                run = false;
//            }
//        } while (!run);
//        return recipe;
//    }

//    public void removeRecipe() {
//        Recipe recipe = new Recipe();
//        Scanner scanner;
//        int removeKey = 0;
//
//        boolean run = false;
//
//        do {
//            try {
//                scanner = new Scanner(System.in);
//                System.out.println("Tytuł przepisu:");
//                String recipeName = scanner.nextLine();
//
//                RecipeService recipeService = new RecipeService();
//                List<Recipe> listRecipe = null;
//                try {
//                    listRecipe = recipeService.getJson();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                for (Recipe recipe1 : listRecipe) {
//
//                    if (recipe1.getName().equalsIgnoreCase(recipeName)) {
//                        removeKey = listRecipe.indexOf(recipe1);
//                    }
//                }
//                listRecipe.remove(removeKey);
//                recipeService.writeJson(listRecipe);
//                run = true;
//            } catch (InputMismatchException | IOException e) {
//                System.out.println("Niepoprawny format odpowiedzi.");
//                run = false;
//            }
//        } while (!run);
//
//
//    }

    //----------------------WEB----------------------
    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipe() {
        return recipeRepository.findAll();
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }

}