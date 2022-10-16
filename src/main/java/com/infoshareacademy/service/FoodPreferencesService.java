package com.infoshareacademy.service;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import org.apache.commons.lang3.StringUtils;
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
        foodPreferences.setId(fridgeService.getDEFAULT_FRIDGE_ID());
        foodPreferencesRepository.save(foodPreferences);
    }

    public FoodPreferences checkIfFoodPreferencesIsSet(FoodPreferences foodPreferences) {
        if (foodPreferencesRepository.findById(fridgeService.getDEFAULT_FRIDGE_ID()).isPresent()) {
            foodPreferences = foodPreferencesRepository.findById(fridgeService.getDEFAULT_FRIDGE_ID()).get();
            return foodPreferences;
        } else {
            return new FoodPreferences();
        }
    }

    public Page<Recipe> filterRecipeByFoodPreferences(Long id, Pageable pageable) {

        Optional<FoodPreferences> foodPreferencesRepositoryById = getFoodPreferencesById(id);
        List<Recipe> recipeList = recipeService.getAllRecipe();

        if (foodPreferencesRepositoryById.isPresent()) {
            FoodPreferences foodPreferences = foodPreferencesRepositoryById.get();
            if (foodPreferences.isChocolate()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isChocolate())
                        .toList();
            }
            if (foodPreferences.isNuts()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isNuts())
                        .toList();
            }
            if (foodPreferences.isEggs()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isEggs())
                        .toList();
            }
            if (foodPreferences.isStrawberries()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isStrawberries())
                        .toList();
            }
            if (foodPreferences.isShellfish()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isShellfish())
                        .toList();
            }

            if (foodPreferences.isDairy()) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().isDairy())
                        .toList();
            }


            if (isNotEqual("-", foodPreferences.getOther())) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().getOther().equals("-"))
                        .toList();
            } else if (isNotEqual("brak", foodPreferences.getOther())) {
                recipeList = recipeList.stream()
                        .filter(s -> !s.getRecipeAllegrens().getOther().equals("brak"))
                        .toList();
            }
            if (foodPreferences.isMeatEater()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isMeatEater())
                        .toList();
            } else if (foodPreferences.isVegetarian()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isVegetarian())
                        .toList();

            } else if (foodPreferences.isVegan()) {
                recipeList = recipeList.stream()
                        .filter(s -> s.getRecipeAllegrens().isVegan())
                        .toList();
            }

        }
        return new PageImpl<>(recipeList);

    }

    private static boolean isNotEqual(String string1, String string2) {
        return !StringUtils.equalsIgnoreCase(string1, string2);
    }
}

