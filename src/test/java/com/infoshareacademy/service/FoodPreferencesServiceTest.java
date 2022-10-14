package com.infoshareacademy.service;

import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllegrens;
import com.infoshareacademy.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FoodPreferencesServiceTest {

    private final RecipeAllergensRepository recipeAllergensRepositoryMock = mock(RecipeAllergensRepository.class);

    private final RecipeRepository recipeRepositoryMock = mock(RecipeRepository.class);
    private final RecipeService recipeService = new RecipeService(recipeRepositoryMock, recipeAllergensRepositoryMock);

    private final FridgeRepository fridgeRepositoryMock = mock(FridgeRepository.class);
    private final ProductInFridgeRepository productInFridgeRepository = mock(ProductInFridgeRepository.class);

    private final FridgeService fridgeService = new FridgeService(fridgeRepositoryMock, productInFridgeRepository);
    private final FoodPreferencesRepository preferencesRepositoryMock = mock(FoodPreferencesRepository.class);

    private final FoodPreferencesService foodPreferencesService = new FoodPreferencesService(preferencesRepositoryMock, recipeService, fridgeService);
    private final Optional<FoodPreferences> foodPreferencesOptional = Optional.of(new FoodPreferences(false, false, false, true, false, false, "-", true, false, false));


    private List<Recipe> createRecipesToTest() {
        ProductRecipe firstProduct = new ProductRecipe("first product", 1.0);
        ProductRecipe secondProduct = new ProductRecipe("second product", 2.0);
        ProductRecipe thirdProduct = new ProductRecipe("third product", 3.0);

        RecipeAllegrens firstAllergens = new RecipeAllegrens(false, false, false, true, true, false, "-", true, false, false);
        RecipeAllegrens secondAllergens = new RecipeAllegrens(true, false, false, false, false, false, "-", true, false, false);
        RecipeAllegrens thirdAllergens = new RecipeAllegrens(false, false, false, true, false, true, "-", false, false, true);

        Recipe firstRecipe = new Recipe("first", "first recipe", 15, List.of(firstProduct), firstAllergens);
        Recipe secondRecipe = new Recipe("second", "second recipe", 30, Arrays.asList(firstProduct, secondProduct), secondAllergens);
        Recipe thirdRecipe = new Recipe("third", "third recipe", 45, Arrays.asList(firstProduct, secondProduct, thirdProduct), thirdAllergens);

        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
    }
        @Test
        void testIfReturnFilteredRecipeByFoodPreferences (Pageable pageable) {
            //given
            List<Recipe> recipesToTest = createRecipesToTest();
            when(recipeService.getAllRecipe()).thenReturn(recipesToTest);
            when(foodPreferencesService.getFoodPreferencesById(any())).thenReturn(foodPreferencesOptional);

            //when
            Page<Recipe> recipes = foodPreferencesService.filterRecipeByFoodPreferences(fridgeService.getUserId(), pageable);

            //then
            assertThat(recipes).hasSize(2)
                    .contains(recipesToTest.get(0), recipesToTest.get(1));

        }
    }