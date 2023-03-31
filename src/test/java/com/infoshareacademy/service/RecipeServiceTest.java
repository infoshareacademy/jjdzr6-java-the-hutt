package com.infoshareacademy.service;

import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.repository.RecipeAllergensRepository;
import com.infoshareacademy.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;


@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeAllergensRepository allergensRepositoryMock;
    @InjectMocks
    RecipeService recipeService;

    @Mock
    ModelMapper modelMapper;

    @Mock
    Pageable pageable;

    @Mock
    private FridgeService fridgeService;


    @Test
    void getAllRecipe() {
        //given
        List<RecipeDto> listOfRecipesDto = createRecipesDtoToTest();
        List<Recipe> recipesToTest = createRecipesToTest();
        when(recipeRepository.findAll()).thenReturn(recipesToTest);
        when(modelMapper.map(recipesToTest.get(0), RecipeDto.class)).thenReturn(listOfRecipesDto.get(0));
        when(modelMapper.map(recipesToTest.get(1), RecipeDto.class)).thenReturn(listOfRecipesDto.get(1));
        when(modelMapper.map(recipesToTest.get(2), RecipeDto.class)).thenReturn(listOfRecipesDto.get(2));


        //when
        List<RecipeDto> allRecipe = recipeService.getAllRecipes();

        //then
        assertThat(allRecipe).hasSize(3)
                .contains(listOfRecipesDto.get(0), listOfRecipesDto.get(1), listOfRecipesDto.get(2));
    }

    @Test
    public void testGetSearchedRecipe() {
        List<Recipe> recipes = createRecipesToTest();
        List<RecipeDto> recipesDtoToTest = createRecipesDtoToTest();
        Mockito.when(recipeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(recipes));
        Mockito.when(recipeRepository.findRecipeBy(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(recipes.get(0))));
        when(modelMapper.map(recipes.get(0), RecipeDto.class)).thenReturn(recipesDtoToTest.get(0));

        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipeDto> result = recipeService.getSearchedRecipe("first", pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("first", result.getContent().get(0).getName());
    }

    @Test
    void testSetUserIdForInitRecipes() {
        // Given
        List<Recipe> recipes = createRecipesToTest();
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);

        when(fridgeService.getUserId()).thenReturn(1L);

        // When
        recipeService.setUserIdForInitRecipes();

        // Then
        verify(recipeRepository, times(1)).findAll();
        assertEquals(1L, recipes.get(0).getUserId().longValue());
    }

    @Test
    void tesFindRecipeById() {
        // Given
        List<Recipe> recipes = createRecipesToTest();
        List<RecipeDto> recipesDtoToTest = createRecipesDtoToTest();
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipes.get(1)));
        when(modelMapper.map(recipes.get(1), RecipeDto.class)).thenReturn(recipesDtoToTest.get(1));


        // When
        RecipeDto recipeById = recipeService.getRecipeById(2L);

        // Then
        verify(recipeRepository, times(1)).findById(2L);
        assertEquals("second", recipeById.getName().toString());
    }

    @Test
    void getRecipesFilteredByMeal() {

        //given
        List<RecipeDto> listOfRecipes = createRecipesDtoToTest();
        List<Recipe> recipesToTest = createRecipesToTest();
        when(recipeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(recipesToTest));
        when(recipeRepository.findRecipeByMeal(Mockito.any(Meal.class), Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(recipesToTest.get(0))));
        when(modelMapper.map(recipesToTest.get(0), RecipeDto.class)).thenReturn(listOfRecipes.get(0));

        //when
        Page<RecipeDto> recipesByMeal = recipeService.getRecipesFilteredByMeal(Meal.BREAKFAST, pageable);

        //then
        assertThat(recipesByMeal).hasSize(1).contains(listOfRecipes.get(0));
    }

    private static List<Recipe> createRecipesToTest() {
        ProductRecipe firstProduct = new ProductRecipe(1L, "first product", 1.0, ProductUnit.GRAM, null);
        ProductRecipe secondProduct = new ProductRecipe(2L, "second product", 2.0, ProductUnit.GRAM, null);
        ProductRecipe thirdProduct = new ProductRecipe(3L, "third product", 3.0, ProductUnit.GRAM, null);

        RecipeAllergens firstAllergens = new RecipeAllergens(null, true, true, true, true, true, true, "a", true, true, true, null);
        RecipeAllergens secondAllergens = new RecipeAllergens();
        secondAllergens.setChocolate(true);
        secondAllergens.setOther("b");
        RecipeAllergens thirdAllergens = new RecipeAllergens();
        thirdAllergens.setChocolate(false);
        thirdAllergens.setOther("c");

        Recipe firstRecipe = new Recipe(1L, "first", "first recipe", 15, Meal.BREAKFAST, List.of(firstProduct), null, firstAllergens, 1L);
        Recipe secondRecipe = new Recipe(2L, "second", "second recipe", 30, Meal.DINNER, Arrays.asList(firstProduct, secondProduct), null, secondAllergens, 1L);
        Recipe thirdRecipe = new Recipe(3L, "third", "third recipe", 45, Meal.LUNCH, Arrays.asList(firstProduct, secondProduct, thirdProduct), null, thirdAllergens, 1L);

        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
    }

    private static List<RecipeDto> createRecipesDtoToTest() {
        RecipeDto.ProductRecipeDto firstProduct = new RecipeDto.ProductRecipeDto(1L, "first product", 1.0, ProductUnit.GRAM, null);
        RecipeDto.ProductRecipeDto secondProduct = new RecipeDto.ProductRecipeDto(2L, "second product", 2.0, ProductUnit.GRAM, null);
        RecipeDto.ProductRecipeDto thirdProduct = new RecipeDto.ProductRecipeDto(3L, "third product", 3.0, ProductUnit.GRAM, null);

        RecipeDto.RecipeAllergensDto firstAllergens = new RecipeDto.RecipeAllergensDto();
        firstAllergens.setChocolate(true);
        firstAllergens.setOther("a");
        RecipeDto.RecipeAllergensDto secondAllergens = new RecipeDto.RecipeAllergensDto();
        secondAllergens.setChocolate(true);
        secondAllergens.setOther("b");
        RecipeDto.RecipeAllergensDto thirdAllergens = new RecipeDto.RecipeAllergensDto();
        thirdAllergens.setChocolate(false);
        thirdAllergens.setOther("c");

        RecipeDto firstRecipe = new RecipeDto(1L, "first", "first recipe", 15, Meal.BREAKFAST, List.of(firstProduct), null, firstAllergens, 1L);
        RecipeDto secondRecipe = new RecipeDto(2L, "second", "second recipe", 30, Meal.DINNER, Arrays.asList(firstProduct, secondProduct), null, secondAllergens, 1L);
        RecipeDto thirdRecipe = new RecipeDto(3L, "third", "third recipe", 45, Meal.LUNCH, Arrays.asList(firstProduct, secondProduct, thirdProduct), null, thirdAllergens, 1L);

        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
    }
}