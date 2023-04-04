package com.infoshareacademy.service;

import com.infoshareacademy.DTO.RecipeAllergensDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.repository.RecipeAllergensRepository;
import com.infoshareacademy.repository.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
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
    RecipeAllergensRepository allergensRepository;

    RecipeService recipeService;

    @Mock
    Pageable pageable;

    @Mock
    private FridgeService fridgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeService(recipeRepository, allergensRepository, fridgeService, new ModelMapper());
    }


    @Test
    void testShouldReturnAllRecipes() {
        //given
        List<RecipeDto> listOfRecipesDto = createRecipesDtoToTest();
        List<Recipe> recipesToTest = createRecipesToTest();
        when(recipeRepository.findAll()).thenReturn(recipesToTest);


        //when
        List<RecipeDto> allRecipe = recipeService.getAllRecipes();

        //then
        assertThat(allRecipe).hasSize(3)
                .contains(listOfRecipesDto.get(0), listOfRecipesDto.get(1), listOfRecipesDto.get(2));
    }

    @Test
    void testGetSearchedRecipe() {
        List<Recipe> recipes = createRecipesToTest();
        Mockito.when(recipeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(recipes));
        Mockito.when(recipeRepository.findRecipeBy(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(recipes.get(0))));

        Pageable pageable = PageRequest.of(0, 10);
        Page<RecipeDto> result = recipeService.getSearchedRecipe("first", pageable);

        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals("first", result.getContent().get(0).getName());
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
        Assertions.assertEquals(1L, recipes.get(0).getUserId().longValue());
    }

    @Test
    void tesFindRecipeById() {
        // Given
        List<Recipe> recipes = createRecipesToTest();
        Mockito.when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipes.get(1)));

        // When
        RecipeDto recipeById = recipeService.getRecipeById(2L);

        // Then
        verify(recipeRepository, times(1)).findById(2L);
        Assertions.assertEquals("second", recipeById.getName());
    }

    @Test
    void shouldReturnRecipesFilteredByMeal() {

        //given
        List<RecipeDto> listOfRecipes = createRecipesDtoToTest();
        List<Recipe> recipesToTest = createRecipesToTest();
        when(recipeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(recipesToTest));
        when(recipeRepository.findRecipeByMeal(Mockito.any(Meal.class), Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(recipesToTest.get(0))));

        //when
        Page<RecipeDto> recipesByMeal = recipeService.getRecipesFilteredByMeal(Meal.BREAKFAST, pageable);

        //then
        assertThat(recipesByMeal).hasSize(1).contains(listOfRecipes.get(0));
    }

    @Test
    void shouldSaveRecipeTest() {
        //given
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setName("Test Recipe");
        recipeDto.setDescription("This is a test recipe");
        recipeDto.setMeal(Meal.BREAKFAST);
        recipeDto.setPreparationTime(10);
        RecipeDto.RecipeAllergensDto recipeAllergens = new RecipeDto.RecipeAllergensDto();
        recipeDto.setRecipeAllergens(recipeAllergens);
        List<RecipeDto.ProductRecipeDto> products = new ArrayList<>();
        RecipeDto.ProductRecipeDto product1 = new RecipeDto.ProductRecipeDto();
        product1.setProductName("Eggs");
        product1.setAmount(2.0);
        product1.setUnit(ProductUnit.PIECE);
        products.add(product1);

        RecipeDto.ProductRecipeDto product2 = new RecipeDto.ProductRecipeDto();
        product2.setProductName("Milk");
        product2.setAmount(100.0);
        product2.setUnit(ProductUnit.MILILITR);
        products.add(product2);

        recipeDto.setProductList(products);

        when(fridgeService.getUserId()).thenReturn(1L);
        when(recipeRepository.save(any())).thenReturn(new Recipe());

        //when
        recipeService.saveRecipe(recipeDto);

        //then
        verify(recipeRepository).save(any(Recipe.class));
    }

    @Test
    void shouldSaveRecipeAllergens() {
        // given
        Recipe recipe = new Recipe();
        recipe.setRecipeId(1L);
        recipe.setName("Test Recipe");
        when(fridgeService.getUserId()).thenReturn(1L);

        RecipeAllergensDto allergens = new RecipeAllergensDto();
        allergens.setChocolate(true);
        allergens.setEggs(true);
        allergens.setShellfish(true);

        RecipeAllergens allergen = new RecipeAllergens();
        allergen.setChocolate(true);
        allergen.setEggs(true);
        allergen.setShellfish(true);
        recipe.setRecipeAllergens(allergen);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));

        //when
        recipeService.saveRecipeAllergens(2L, allergens);

        // then
        verify(allergensRepository).save(any());
        Assertions.assertTrue(recipe.getRecipeAllergens().isChocolate());
        Assertions.assertTrue(recipe.getRecipeAllergens().isEggs());
        Assertions.assertTrue(recipe.getRecipeAllergens().isShellfish());

    }

    @Test
    void shouldUpdateRecipe() {
        // given
        RecipeDto recipe = new RecipeDto();
        recipe.setName("Old Recipe Name");
        recipe.setDescription("Old Recipe Description");
        recipe.setPreparationTime(60);
        recipe.setMeal(Meal.LUNCH);

        when(fridgeService.getUserId()).thenReturn(1L);

        // when
        recipeService.updateRecipe(1L, recipe);

        // then
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void shouldDeleteRecipeById() {
        // given
        Long recipeId = 1L;
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeId);

        // when
        recipeService.deleteRecipeById(recipeId);

        // then
        verify(recipeRepository, times(1)).deleteByRecipeId(recipeId);
    }

    @Test
    void shouldDeleteAllRecipes() {

        // when
        recipeService.deleteAllRecipes();

        // then
        verify(recipeRepository, times(1)).deleteAll();
    }

    @Test
    void shouldReturnRecipesWithProductsToLowerCase() {
        //given
        List<Recipe> recipesToTest = createRecipesToTest();
        when(recipeRepository.findAll()).thenReturn(recipesToTest);

        //when
        List<RecipeDto> lowerCaseRecipes = recipeService.getRecipesWithProductsToLowerCase();
        //then
        Assertions.assertEquals("first product", lowerCaseRecipes.get(0).getProductList().get(0).getProductName());
    }

    @Test
    void shouldConvertUnitsInProducts() {
        //given
        ProductRecipe productRecipe = new ProductRecipe();
        productRecipe.setProductName("First");
        productRecipe.setAmount(10000.0);
        productRecipe.setUnit(ProductUnit.MILIGRAM);

        //when
        recipeService.convertUnitsInProducts(productRecipe);
        //then
        Assertions.assertEquals(10.0, productRecipe.getAmount());
        Assertions.assertEquals(ProductUnit.GRAM, productRecipe.getUnit());


    }


    private static List<Recipe> createRecipesToTest() {
        ProductRecipe firstProduct = new ProductRecipe(1L, "First product", 1.0, ProductUnit.GRAM, null);
        ProductRecipe secondProduct = new ProductRecipe(2L, "Second product", 2.0, ProductUnit.GRAM, null);
        ProductRecipe thirdProduct = new ProductRecipe(3L, "Third product", 3.0, ProductUnit.GRAM, null);

        RecipeAllergens firstAllergens = new RecipeAllergens(null, true, true, true, true, true, true, "a", true, true, true, null);
        RecipeAllergens secondAllergens = new RecipeAllergens();
        secondAllergens.setChocolate(true);
        secondAllergens.setOther("b");
        RecipeAllergens thirdAllergens = new RecipeAllergens();
        thirdAllergens.setChocolate(false);
        thirdAllergens.setOther("c");

        Recipe firstRecipe = new Recipe(1L, "first", "First recipe", 15, Meal.BREAKFAST, List.of(firstProduct), null, firstAllergens, 1L);
        Recipe secondRecipe = new Recipe(2L, "second", "Second recipe", 30, Meal.DINNER, Arrays.asList(firstProduct, secondProduct), null, secondAllergens, 1L);
        Recipe thirdRecipe = new Recipe(3L, "third", "Third recipe", 45, Meal.LUNCH, Arrays.asList(firstProduct, secondProduct, thirdProduct), null, thirdAllergens, 1L);

        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
    }

    private static List<RecipeDto> createRecipesDtoToTest() {
        RecipeDto.ProductRecipeDto firstProduct = new RecipeDto.ProductRecipeDto(1L, "First product", 1.0, ProductUnit.GRAM, null);
        RecipeDto.ProductRecipeDto secondProduct = new RecipeDto.ProductRecipeDto(2L, "Second product", 2.0, ProductUnit.GRAM, null);
        RecipeDto.ProductRecipeDto thirdProduct = new RecipeDto.ProductRecipeDto(3L, "Third product", 3.0, ProductUnit.GRAM, null);

        RecipeDto.RecipeAllergensDto firstAllergens = new RecipeDto.RecipeAllergensDto(null, true, true, true, true, true, true, "a", true, true, true, null);
        RecipeDto.RecipeAllergensDto secondAllergens = new RecipeDto.RecipeAllergensDto();
        secondAllergens.setChocolate(true);
        secondAllergens.setOther("b");
        RecipeDto.RecipeAllergensDto thirdAllergens = new RecipeDto.RecipeAllergensDto();
        thirdAllergens.setChocolate(false);
        thirdAllergens.setOther("c");

        RecipeDto firstRecipe = new RecipeDto(1L, "first", "First recipe", 15, Meal.BREAKFAST, List.of(firstProduct), null, firstAllergens, 1L);
        RecipeDto secondRecipe = new RecipeDto(2L, "second", "Second recipe", 30, Meal.DINNER, Arrays.asList(firstProduct, secondProduct), null, secondAllergens, 1L);
        RecipeDto thirdRecipe = new RecipeDto(3L, "third", "Third recipe", 45, Meal.LUNCH, Arrays.asList(firstProduct, secondProduct, thirdProduct), null, thirdAllergens, 1L);

        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
    }
}

