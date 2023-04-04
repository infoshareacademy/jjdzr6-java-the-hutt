package com.infoshareacademy.service;

import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.DTO.ProductRecipeDto;
import com.infoshareacademy.DTO.RecipeDto;
import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import com.infoshareacademy.repository.ProductRecipeRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ProductRecipeServiceTest {

    ProductRecipeService productRecipeService;

    @Mock
    ProductRecipeRepository productRecipeRepository;

    @Mock
    RecipeService recipeService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productRecipeService = new ProductRecipeService(productRecipeRepository, recipeService, new ModelMapper());

    }
    @Test
    void getAllProductRecipeByRecipeId() {
        //given
        List<ProductRecipe> defaultProductList = getDefaultProductList();
        when(productRecipeRepository.findAllProductsByRecipeRecipeId(1L)).thenReturn(defaultProductList);
        //when
        List<ProductRecipeDto> listOfRecipes = productRecipeService.getAllProductRecipeByRecipeId(1L);
        //then
        assertEquals(defaultProductList.get(0).getProductName(),listOfRecipes.get(0).getProductName());
    }

    @Test
    void findProductRecipeById() throws Exception {
        //given
        RecipeDto.ProductRecipeDto productRecipeDto = createRecipesDtoToTest().get(0).getProductList().get(0);
        ProductRecipe productRecipe = createRecipesToTest().get(0).getProductList().get(0);
        when(productRecipeRepository.findById(1L)).thenReturn(Optional.of(productRecipe));
        //when
        ProductRecipeDto productRecipeById = productRecipeService.findProductRecipeById(1L);
        //then
        assertEquals(productRecipeById.getProductName(), productRecipeDto.getProductName());
        assertThrows(Exception.class, () -> productRecipeService.findProductRecipeById(3L));

    }

    @Test
    void deleteProductRecipe() throws Exception {
        //given
        RecipeDto.ProductRecipeDto productRecipeDto = createRecipesDtoToTest().get(0).getProductList().get(0);
        ProductRecipe productRecipe = createRecipesToTest().get(0).getProductList().get(0);
        when(productRecipeRepository.findById(1L)).thenReturn(Optional.of(productRecipe));
        //when
        productRecipeService.deleteProductRecipe(1L);
        //then
        verify(productRecipeRepository, times(1)).delete(productRecipe);
        assertThrows(Exception.class, () -> productRecipeService.deleteProductRecipe(3L));
    }

    @Test
    void saveProductRecipe() {
        //given
        RecipeDto recipeDto = createRecipesDtoToTest().get(0);
        RecipeDto.ProductRecipeDto product = new RecipeDto.ProductRecipeDto();
        product.setProductName("woda");
        product.setAmount(50.0);
        product.setUnit(ProductUnit.MILILITR);
        ProductRecipe productSaved = new ProductRecipe();
        productSaved.setProductName("woda");
        productSaved.setAmount(50.0);
        productSaved.setUnit(ProductUnit.MILILITR);

        //when
        productRecipeService.saveProductRecipe(product, recipeDto);

        //then
        verify(productRecipeRepository, times(1)).save(productSaved);
        assertThrows(Exception.class, () -> productRecipeService.saveProductRecipe(null, recipeDto));

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

    private List<ProductRecipe> getDefaultProductList() {
        ProductRecipe firstPrduct = new ProductRecipe();
        firstPrduct.setProductId(1L);
        firstPrduct.setProductName("Jajka");
        firstPrduct.setAmount(1.0);
        firstPrduct.setUnit(ProductUnit.GRAM);


        ProductRecipe secondProduct = new ProductRecipe();
        secondProduct.setProductId(2L);
        secondProduct.setProductName("Jabłka");
        secondProduct.setAmount(2.0);
        secondProduct.setUnit(ProductUnit.LITR);


        return List.of(firstPrduct,secondProduct);
    }
}