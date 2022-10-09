package com.infoshareacademy.service;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class FoodPreferencesService {


    private final FoodPreferencesRepository foodPreferencesRepository;

    private final RecipeService recipeService;

    private final FridgeService fridgeService;

    @Autowired
    public FoodPreferencesService(FoodPreferencesRepository foodPreferencesRepository, RecipeService recipeService, FridgeService fridgeService) {
        this.foodPreferencesRepository = foodPreferencesRepository;
        this.recipeService = recipeService;
        this.fridgeService = fridgeService;
    }

    public List<FoodPreferences> getFoodPreferences() {
        return foodPreferencesRepository.findAll();
    }

    public Optional<FoodPreferences> getFoodPreferencesById(Long id) {
        return foodPreferencesRepository.findById(id);
    }

    public void setFoodPreferences(FoodPreferences foodPreferences) {
        foodPreferences.setId(fridgeService.getUserId());
        foodPreferencesRepository.save(foodPreferences);
    }

    public FoodPreferences checkIfFoodPreferencesIsSet(FoodPreferences foodPreferences) {
        if (foodPreferencesRepository.findById(fridgeService.getUserId()).isPresent()) {
            foodPreferences = foodPreferencesRepository.findById(fridgeService.getUserId()).get();
            return foodPreferences;
        } else {
            return new FoodPreferences();
        }
    }

    public Page<Recipe> filterRecipeByFoodPreferences(Long id, Pageable pageable) {

        Optional<FoodPreferences> foodPreferencesRepositoryById = getFoodPreferencesById(id);
        List<Recipe> recipeList = recipeService.getAllRecipe();

        if (foodPreferencesRepositoryById.isPresent()) {
            if (foodPreferencesRepositoryById.get().isChocolate()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isChocolate())
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isNuts()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isNuts())
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isEggs()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isEggs())
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isStrawberries()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isStrawberries())
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isShellfish()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isShellfish())
                        .toList();
            }

            if (foodPreferencesRepositoryById.get().isDairy()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isDairy())
                        .toList();
            }

            if (!foodPreferencesRepositoryById.get().getOther().equals("-")) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().getOther().equals("-"))
                        .toList();
            } else if (!foodPreferencesRepositoryById.get().getOther().equals("brak")) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().getOther().equals("brak"))
                        .toList();
            }
            if (foodPreferencesRepositoryById.get().isMeatEater()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isMeatEater())
                        .toList();
            } else if (foodPreferencesRepositoryById.get().isVegetarian()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isVegetarian())
                        .toList();

            } else if (foodPreferencesRepositoryById.get().isVegan()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isVegan())
                        .toList();
            }

        }
        return new PageImpl<>(recipeList);

    }

}

