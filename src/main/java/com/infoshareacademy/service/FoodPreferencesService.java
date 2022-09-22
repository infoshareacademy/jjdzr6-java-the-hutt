package com.infoshareacademy.service;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FoodPreferencesService {


    private final FoodPreferencesRepository foodPreferencesRepository;

    private final RecipeService recipeService;

    @Autowired
    public FoodPreferencesService(FoodPreferencesRepository foodPreferencesRepository, RecipeService recipeService) {
        this.foodPreferencesRepository = foodPreferencesRepository;
        this.recipeService = recipeService;
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

    public List<Recipe> filterRecipeByFoodPreferences(Long id) {

        Optional<FoodPreferences> foodPreferencesRepositoryById = foodPreferencesRepository.findById(id);
        List<Recipe> recipeList = recipeService.getAllRecipe();

        if (foodPreferencesRepositoryById.isPresent()) {
            if (foodPreferencesRepositoryById.get().isChocolate()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct("czekolada"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isNuts()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct("orzechy"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isEggs()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct("jajka"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isStrawberries()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct("truskawki"))
                        .toList();
            }

/*            if (foodPreferencesRepositoryById.get().isVegetarian()) {
                recipeList = recipeList.stream()
                        .filter(Recipe::isVegetarian)
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isVegan()) {
                recipeList = recipeList.stream()
                        .filter(Recipe::isVegan)
                        .toList();
            }*/
            if (foodPreferencesRepositoryById.get().isDairy()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct("mleko"))
                        .toList();
            }
/*            if (!foodPreferencesRepositoryById.get().getOther().equals("-")) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct(foodPreferencesRepositoryById.get().getOther()))
                        .toList();
            } else if (!foodPreferencesRepositoryById.get().getOther().equals("brak")) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.containsProduct(foodPreferencesRepositoryById.get().getOther()))
                        .toList();
            }*/
        }
        return recipeList;

    }

}

