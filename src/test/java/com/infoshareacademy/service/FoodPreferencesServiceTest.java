package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FoodPreferencesDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.food_preferences.FoodPreferences;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.repository.FoodPreferencesRepository;
import com.infoshareacademy.repository.RecipeRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.testng.AssertJUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertTrue;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class FoodPreferencesServiceTest {

    @Mock
    private FoodPreferencesRepository foodPreferencesRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private FridgeService fridgeService;

    @InjectMocks
    private FoodPreferencesService foodPreferencesService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Mock
    Pageable page;


    @Test
    public void testGetFoodPreferencesById() {
        // Given
        Long userId = 1L;
        FoodPreferences foodPreferences = new FoodPreferences();
        foodPreferences.setId(userId);
        when(fridgeService.getUserId()).thenReturn(userId);
        when(foodPreferencesRepository.findById(userId)).thenReturn(Optional.of(foodPreferences));
        FoodPreferencesDto foodPreferencesDto = new FoodPreferencesDto();
        when(modelMapper.map(foodPreferences, FoodPreferencesDto.class)).thenReturn(foodPreferencesDto);

        // When
        Optional<FoodPreferencesDto> result = foodPreferencesService.getFoodPreferencesById();

        // Then
        assertEquals(Optional.of(foodPreferencesDto), result);
    }


    @Test
    public void testSetFoodPreferences() {
        // Given
        Long id = 1L;
        FoodPreferencesDto foodPreferencesDto = new FoodPreferencesDto();
        foodPreferencesDto.setId(id);
        FoodPreferences foodPreferences = new FoodPreferences();
        when(fridgeService.getUserId()).thenReturn(id);
        when(modelMapper.map(foodPreferencesDto, FoodPreferences.class)).thenReturn(foodPreferences);

        // When
        foodPreferencesService.setFoodPreferences(foodPreferencesDto);

        // Then
        verify(foodPreferencesRepository, times(1)).save(foodPreferences);
    }

    @Test
    public void checkIfFoodPreferencesIsSetTest() {
        // Given
        Long id = 1L;
        FoodPreferences foodPreferences = new FoodPreferences();
        when(fridgeService.getUserId()).thenReturn(id);
        when(foodPreferencesRepository.findById(id)).thenReturn(Optional.of(foodPreferences));
        FoodPreferencesDto foodPreferencesDto = new FoodPreferencesDto();
        when(modelMapper.map(foodPreferences, FoodPreferencesDto.class)).thenReturn(foodPreferencesDto);

        // When
        Optional<FoodPreferencesDto> result = foodPreferencesService.checkIfFoodPreferencesIsSet();

        // Then
        assertTrue(result.isPresent());
    }

    @Test
    public void testCreatePageFromList() {
        List<Recipe> list = new ArrayList<>();
        Recipe recipe1 = new Recipe();
        recipe1.setName("recipe1");
        Recipe recipe2 = new Recipe();
        recipe2.setName("recipe2");
        Recipe recipe3 = new Recipe();
        recipe3.setName("recipe3");
        list.add(recipe1);
        list.add(recipe2);
        list.add(recipe3);

        when(page.getPageNumber()).thenReturn(0);
        when(page.getPageSize()).thenReturn(2);

        Page<Recipe> pages = FoodPreferencesService.createPageFromList(list, page);

        assertNotNull(pages);
        assertEquals(2, pages.getNumberOfElements());
        assertEquals(3, pages.getTotalElements());
        assertEquals(2, pages.getTotalPages());
        assertEquals("recipe1", pages.getContent().get(0).getName());
        assertEquals("recipe2", pages.getContent().get(1).getName());
    }

    @Test
    public void testIfFilterRecipeByFoodPreferences() {

        //given
        List<Recipe> recipesToTest = createRecipesToTest();
        Pageable pageable = PageRequest.of(0, 5);

        //mock food preferences
        Long userId = 1L;
        FoodPreferences foodPreferences = new FoodPreferences();
        foodPreferences.setId(userId);
        when(fridgeService.getUserId()).thenReturn(userId);
        when(foodPreferencesRepository.findById(userId)).thenReturn(Optional.of(foodPreferences));
        FoodPreferencesDto foodPreferencesDto = new FoodPreferencesDto();
        foodPreferencesDto.setNuts(true);
        foodPreferencesDto.setOther("-");
        foodPreferencesDto.setChocolate(true);
        when(modelMapper.map(foodPreferences, FoodPreferencesDto.class)).thenReturn(foodPreferencesDto);

        List<RecipeDto> recipesDtoToTest = createRecipesDtoToTest();
        when(recipeRepository.findAll()).thenReturn(recipesToTest);
        when(recipeRepository.findAll(pageable)).thenReturn(new PageImpl<>(recipesToTest));
        when(modelMapper.map(recipesToTest.get(0), RecipeDto.class)).thenReturn(recipesDtoToTest.get(0));
        when(modelMapper.map(recipesToTest.get(1), RecipeDto.class)).thenReturn(recipesDtoToTest.get(1));
        when(modelMapper.map(recipesToTest.get(2), RecipeDto.class)).thenReturn(recipesDtoToTest.get(2));

        //when
        Page<RecipeDto> recipeDtos = foodPreferencesService.filterRecipeByFoodPreferences(pageable);
        List<RecipeDto> resultList = recipeDtos.stream().toList();

        //then
        assertEquals(1, resultList.size());
        AssertJUnit.assertEquals(3L, (long) resultList.get(0).getRecipeId());
        AssertJUnit.assertEquals(resultList.get(0).getMeal(), Meal.LUNCH);
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





