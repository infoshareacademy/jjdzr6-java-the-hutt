package com.infoshareacademy.service;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FoodPreferencesService {

    @Autowired
    private FoodPreferencesRepository foodPreferencesRepository;
    @Autowired
    RecipeService recipeService;

    public FoodPreferencesService(FoodPreferencesRepository foodPreferencesRepository) {
        this.foodPreferencesRepository = foodPreferencesRepository;
    }

    public FoodPreferencesService() {

    }

    public List<FoodPreferences> getFoodPreferences() {
        return foodPreferencesRepository.findAll();
    }

    public Optional<FoodPreferences> getFoodPreferencesById(Long id) {
        return foodPreferencesRepository.findById(id);

    }

    public void setFoodPreferences(FoodPreferences foodPreferences) {
        foodPreferencesRepository.save(foodPreferences);
    }

    public List<Recipe> filterRecipeByFoodPreferences(Long id) throws IOException {

        Optional<FoodPreferences> foodPreferencesRepositoryById = foodPreferencesRepository.findById(id);

        List<Recipe> recipeList = recipeService.getJson();

        if (foodPreferencesRepositoryById.isPresent()) {
            if (foodPreferencesRepositoryById.get().isChocolate()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains("czekolada"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isNuts()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains("orzechy"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isEggs()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains("jajka"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isStrawberries()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains("truskawki"))
                        .toList();
            }

            if (foodPreferencesRepositoryById.get().isVegetarian()) {
                recipeList = recipeList.stream()
                        .filter(Recipe::isVegetarian)
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isVegan()) {
                recipeList = recipeList.stream()
                        .filter(Recipe::isVegan)
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isDairy()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains("mleko"))
                        .toList();
            }
            if (!foodPreferencesRepositoryById.get().getOther().equals("-")) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains(foodPreferencesRepositoryById.get().getOther()))
                        .toList();
            } else if (!foodPreferencesRepositoryById.get().getOther().equals("brak")) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getProductList().contains(foodPreferencesRepositoryById.get().getOther()))
                        .toList();
            }
        }
        return recipeList;

    }

}

