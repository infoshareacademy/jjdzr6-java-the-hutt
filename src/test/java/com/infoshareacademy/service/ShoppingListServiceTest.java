package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.DTO.ShoppingListDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
import com.infoshareacademy.entity.user.User;
import com.infoshareacademy.repository.ProductShoppingListRepository;
import com.infoshareacademy.repository.ShoppingListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ShoppingListServiceTest {

    @Mock
    ShoppingListRepository shoppingListRepository;
    @Mock
    FridgeService fridgeService;

    @Mock
    ProductShoppingListRepository productShoppingListRepository;

    @Mock
    ShoppingListService shoppingListService;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shoppingListService = new ShoppingListService(shoppingListRepository, fridgeService, productShoppingListRepository, recipeService, new ModelMapper());
    }

    @Test
    @WithMockUser
    void shouldReturnAllShoppingLists() {
        //given
        List<ShoppingListDto> shoppingListsDtoToTest = createShoppingListsDtoToTest();
        List<ShoppingList> shoppingListsToTest = createShoppingListsToTest();
        User user = getUser();
        when(fridgeService.getUserId()).thenReturn(user.getId());
        when(shoppingListRepository.findByUserId(1L)).thenReturn(shoppingListsToTest);

        //when
        List<ShoppingListDto> allShoppingLists = shoppingListService.findAllShoppingLists();

        //then
        assertEquals(shoppingListsDtoToTest.get(0).getName(), allShoppingLists.get(0).getName());
        assertEquals(2, allShoppingLists.size());
    }

    @Test
    @WithMockUser
    void shouldSaveShoppingList() {
        //given
        ShoppingListDto shoppingListDto = createShoppingListsDtoToTest().get(0);
        User user = getUser();
        when(fridgeService.getUserId()).thenReturn(user.getId());
        //when
        shoppingListService.saveShoppingList(shoppingListDto);
        //then
        verify(shoppingListRepository, times(1)).save(any(ShoppingList.class));
    }

    @Test
    void shouldDeleteShoppingListById() {
        //given
        Long shoppingListId = 1L;
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(shoppingListId);
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));

        //when
        shoppingListService.deleteShoppingListById(1L);

        //then
        verify(shoppingListRepository, times(1)).deleteShoppingListById(shoppingListId);
    }

    @Test
    void shouldReturnShoppingList() {
        //given
        ShoppingListDto shoppingListDto = createShoppingListsDtoToTest().get(0);
        ShoppingList shoppingList = createShoppingListsToTest().get(0);
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));

        //when
        ShoppingListDto returnedShoppingList = shoppingListService.getShoppingList(1L);
        ShoppingListDto emptyShoppingList = shoppingListService.getShoppingList(3L);

        //then
        assertEquals(shoppingListDto.getName(), returnedShoppingList.getName());
        assertNull(emptyShoppingList.getName());
    }

    @Test
    void testIfAddRecipeToShoppingList() {
        //given
        ShoppingList shoppingList = createShoppingListsToTest().get(0);
        RecipeDto recipeDto = createRecipeDto();
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));
        when(recipeService.getRecipeById(1L)).thenReturn(recipeDto);

        //when
        shoppingListService.addRecipeToShoppingList(1L, 1L);
        //then
        verify(shoppingListRepository, times(1)).findById(1L);
        verify(shoppingListRepository, times(1)).save(any());
    }

    @Test
    void testIfAddProductsRecipeToShoppingList() {
        //given
        ShoppingList shoppingList = createShoppingListsToTest().get(0);
        when(shoppingListRepository.findById(1L)).thenReturn(Optional.of(shoppingList));
        when(fridgeService.getFridge()).thenReturn(getDefaultFridgeDto());
        //when
        shoppingListService.addProductsRecipeToShoppingList(1L);
        //then
        verify(shoppingListRepository, times(2)).findById(1L);
        verify(shoppingListRepository, times(1)).save(any());
    }

    private static List<ShoppingListDto> createShoppingListsDtoToTest() {
        ShoppingListDto.ProductShoppingListDto firstProduct = new ShoppingListDto.ProductShoppingListDto();
        firstProduct.setProductName("first product");
        firstProduct.setProductId(1L);
        firstProduct.setAmount(10.0);
        firstProduct.setUnit(ProductUnit.GRAM);
        ShoppingListDto.ProductShoppingListDto secondProduct = new ShoppingListDto.ProductShoppingListDto();
        secondProduct.setProductName("second product");
        secondProduct.setProductId(2L);
        secondProduct.setAmount(20.0);
        secondProduct.setUnit(ProductUnit.GRAM);
        ShoppingListDto.ProductShoppingListDto thirdProduct = new ShoppingListDto.ProductShoppingListDto();
        thirdProduct.setProductName("third product");
        thirdProduct.setProductId(3L);
        thirdProduct.setAmount(300.0);
        thirdProduct.setUnit(ProductUnit.PACK);
        List<ShoppingListDto.RecipeDto> recipesDtoToTest = createRecipesDtoToTest();

        ShoppingListDto first = new ShoppingListDto();
        first.setName("pierwsza");
        first.setShoppingProductList(List.of(firstProduct, thirdProduct));
        first.setShoppingListRecipe(List.of(recipesDtoToTest.get(0), recipesDtoToTest.get(1)));

        ShoppingListDto second = new ShoppingListDto();
        first.setName("druga");
        first.setShoppingProductList(List.of(secondProduct, thirdProduct));
        first.setShoppingListRecipe(List.of(recipesDtoToTest.get(1), recipesDtoToTest.get(2)));
        return List.of(first, second);
    }

    private static List<ShoppingList> createShoppingListsToTest() {
        ProductShoppingList firstProduct = new ProductShoppingList();
        firstProduct.setProductName("first product");
        firstProduct.setProductId(1L);
        firstProduct.setAmount(10.0);
        firstProduct.setUnit(ProductUnit.GRAM);
        ProductShoppingList secondProduct = new ProductShoppingList();
        secondProduct.setProductName("second product");
        secondProduct.setProductId(2L);
        secondProduct.setAmount(20.0);
        secondProduct.setUnit(ProductUnit.GRAM);
        ProductShoppingList thirdProduct = new ProductShoppingList();
        thirdProduct.setProductName("third product");
        thirdProduct.setProductId(3L);
        thirdProduct.setAmount(300.0);
        thirdProduct.setUnit(ProductUnit.PACK);
        List<Recipe> recipesDtoToTest = createRecipesToTest();

        ShoppingList first = new ShoppingList();
        first.setName("pierwsza");
        first.setShoppingProductList(List.of(firstProduct, thirdProduct));
        first.setShoppingListRecipe(List.of(recipesDtoToTest.get(0), recipesDtoToTest.get(1)));

        ShoppingList second = new ShoppingList();
        first.setName("druga");
        first.setShoppingProductList(List.of(secondProduct, thirdProduct));
        first.setShoppingListRecipe(List.of(recipesDtoToTest.get(1), recipesDtoToTest.get(2)));
        return List.of(first, second);
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

    private static List<ShoppingListDto.RecipeDto> createRecipesDtoToTest() {
        ShoppingListDto.RecipeDto.ProductRecipeDto firstProduct = new ShoppingListDto.RecipeDto.ProductRecipeDto(1L, "First product", 1.0, ProductUnit.GRAM, null);
        ShoppingListDto.RecipeDto.ProductRecipeDto secondProduct = new ShoppingListDto.RecipeDto.ProductRecipeDto(2L, "Second product", 2.0, ProductUnit.GRAM, null);
        ShoppingListDto.RecipeDto.ProductRecipeDto thirdProduct = new ShoppingListDto.RecipeDto.ProductRecipeDto(3L, "Third product", 3.0, ProductUnit.GRAM, null);

        RecipeDto.RecipeAllergensDto secondAllergens = new RecipeDto.RecipeAllergensDto();
        secondAllergens.setChocolate(true);
        secondAllergens.setOther("b");
        RecipeDto.RecipeAllergensDto thirdAllergens = new RecipeDto.RecipeAllergensDto();
        thirdAllergens.setChocolate(false);
        thirdAllergens.setOther("c");

        ShoppingListDto.RecipeDto firstRecipe = new ShoppingListDto.RecipeDto();
        firstRecipe.setRecipeId(1L);
        firstRecipe.setMeal(Meal.LUNCH);
        firstRecipe.setDescription("first Recipe");
        firstRecipe.setName("first");
        firstRecipe.setPreparationTime(15);
        firstRecipe.setProductList(List.of(firstProduct));
        ShoppingListDto.RecipeDto secondRecipe = new ShoppingListDto.RecipeDto();
        secondRecipe.setRecipeId(2L);
        secondRecipe.setMeal(Meal.LUNCH);
        secondRecipe.setDescription("second Recipe");
        secondRecipe.setName("second");
        secondRecipe.setPreparationTime(30);
        secondRecipe.setProductList(List.of(firstProduct, secondProduct));
        ShoppingListDto.RecipeDto thirdRecipe = new ShoppingListDto.RecipeDto();
        thirdRecipe.setRecipeId(3L);
        thirdRecipe.setMeal(Meal.SUPPER);
        thirdRecipe.setDescription("third Recipe");
        thirdRecipe.setName("third");
        thirdRecipe.setPreparationTime(45);
        thirdRecipe.setProductList(List.of(firstProduct, secondProduct, thirdProduct));

        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
    }

    private static RecipeDto createRecipeDto() {
        RecipeDto.ProductRecipeDto firstProduct = new RecipeDto.ProductRecipeDto(1L, "First product", 1.0, ProductUnit.GRAM, null);
        RecipeDto firstRecipe = new RecipeDto();
        firstRecipe.setRecipeId(1L);
        firstRecipe.setMeal(Meal.LUNCH);
        firstRecipe.setDescription("first Recipe");
        firstRecipe.setName("first");
        firstRecipe.setPreparationTime(15);
        firstRecipe.setProductList(List.of(firstProduct));
        firstRecipe.setShoppingList(new ArrayList<>());
        return firstRecipe;
    }

    private static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("first");
        return user;
    }

    private static FridgeDto getDefaultFridgeDto() {
        FridgeDto fridgeDto = new FridgeDto();
        fridgeDto.setFridgeId(1L);
        FridgeDto.ProductInFridgeDto first = new FridgeDto.ProductInFridgeDto();
        first.setProductName("mleko");
        first.setAmount(5.0);
        first.setUnit(ProductUnit.LITR);
        FridgeDto.ProductInFridgeDto second = new FridgeDto.ProductInFridgeDto();
        second.setProductName("płatki owsiane");
        second.setAmount(100.0);
        second.setUnit(ProductUnit.GRAM);
        fridgeDto.setProductsInFridge(List.of(first, second));
        return fridgeDto;
    }
}