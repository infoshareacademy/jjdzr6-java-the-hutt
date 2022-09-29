package com.infoshareacademy.service;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllegrens;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.RecipeRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodPreferencesServiceTest {

    private final ProductRecipe firstProduct = new ProductRecipe("first product", 1.0);
    private final ProductRecipe secondProduct = new ProductRecipe("second product", 2.0);
    private final ProductRecipe thirdProduct = new ProductRecipe("third product", 3.0);

    private final RecipeAllegrens firstAllergens = new RecipeAllegrens(false, false, false, true, true, false, "-", true, false, false);
    private final RecipeAllegrens secondAllergens = new RecipeAllegrens(true, false, false, false, false, false, "-", true, false, false);
    private final RecipeAllegrens thirdAllergens = new RecipeAllegrens(false, false, false, true, false, true, "-", false, false, true);

    private final Recipe firstRecipe = new Recipe("first", "first recipe", 15, Arrays.asList(firstProduct), firstAllergens);
    private final Recipe secondRecipe = new Recipe("second", "second recipe", 30, Arrays.asList(firstProduct, secondProduct), secondAllergens);
    private final Recipe thirdRecipe = new Recipe("third", "third recipe", 45, Arrays.asList(firstProduct, secondProduct, thirdProduct), thirdAllergens);

    private RecipeRepository recipeRepositoryMock = mock(RecipeRepository.class);
    private RecipeService recipeService = new RecipeService(recipeRepositoryMock);

    private FridgeRepository fridgeRepositoryMock = mock(FridgeRepository.class);

    private FridgeService fridgeService = new FridgeService(fridgeRepositoryMock);
    private FoodPreferencesRepository preferencesRepositoryMock = mock(FoodPreferencesRepository.class);

    private FoodPreferencesService foodPreferencesService = new FoodPreferencesService(preferencesRepositoryMock, recipeService, fridgeService);
    private Optional<FoodPreferences> foodPreferencesOprional = Optional.of(new FoodPreferences(false, false, false, true, false, false, "-", true, false, false));

    @Test
    void filterRecipeByFoodPreferences() {
        //given
        when(recipeService.getAllRecipe()).thenReturn(Arrays.asList(firstRecipe, secondRecipe, thirdRecipe));
        when(foodPreferencesService.getFoodPreferencesById(any())).thenReturn(foodPreferencesOprional);

        //when
        List<Recipe> recipes = foodPreferencesService.filterRecipeByFoodPreferences(fridgeService.getUserId());

        //then
        assertThat(recipes).hasSize(2)
                .containsExactly(firstRecipe, secondRecipe);

    }
}