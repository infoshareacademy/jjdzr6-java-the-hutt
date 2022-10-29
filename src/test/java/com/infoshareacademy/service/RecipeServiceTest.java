//package com.infoshareacademy.service;
//
//import com.infoshareacademy.entity.product.ProductRecipe;
//import com.infoshareacademy.entity.product.ProductUnit;
//import com.infoshareacademy.entity.recipe.Meal;
//import com.infoshareacademy.entity.recipe.Recipe;
//import com.infoshareacademy.entity.recipe.RecipeAllegrens;
//import com.infoshareacademy.repository.RecipeAllergensRepository;
//import com.infoshareacademy.repository.RecipeRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class RecipeServiceTest {
//
//    public List<Recipe> createListOfRecipes() {
//        final ProductRecipe firstProduct = new ProductRecipe("first product", 1.0, ProductUnit.GRAM);
//        final ProductRecipe secondProduct = new ProductRecipe("second product", 2.0, ProductUnit.LITR);
//        final ProductRecipe thirdProduct = new ProductRecipe("third product", 3.0, ProductUnit.KILOGRAM);
//
//        final RecipeAllegrens recipeAllegrens = new RecipeAllegrens();
//        final Recipe firstRecipe = new Recipe("first", "first recipe", 15, List.of(firstProduct));
//        final Recipe secondRecipe = new Recipe("second", "second recipe", 30, Arrays.asList(firstProduct, secondProduct));
//        final Recipe thirdRecipe = new Recipe("third", "third recipe", 45, Arrays.asList(firstProduct, secondProduct, thirdProduct));
//
//        firstRecipe.setMeal(Meal.BREAKFAST);
//        secondRecipe.setMeal(Meal.DINNER);
//        thirdRecipe.setMeal(Meal.LUNCH);
//
//        firstRecipe.setRecipeAllegrens(recipeAllegrens);
//        secondRecipe.setRecipeAllegrens(recipeAllegrens);
//        thirdRecipe.setRecipeAllegrens(recipeAllegrens);
//
//        thirdRecipe.setRecipeId(3L);
//
//        return Arrays.asList(firstRecipe, secondRecipe, thirdRecipe);
//    }
//
//    RecipeRepository recipeRepositoryMock = mock(RecipeRepository.class);
//
//    RecipeAllergensRepository allergensRepositoryMock = mock(RecipeAllergensRepository.class);
//    private RecipeService recipeService = new RecipeService(recipeRepositoryMock, allergensRepositoryMock);
//
//
//    @Test
//    void getAllRecipe() {
//        //given
//        List<Recipe> listOfRecipes = createListOfRecipes();
//        when(recipeService.getAllRecipe()).thenReturn(listOfRecipes);
//
//        //when
//        List<Recipe> allRecipe = recipeService.getAllRecipe();
//
//        //then
//        assertThat(allRecipe).hasSize(3)
//                .contains(listOfRecipes.get(0), listOfRecipes.get(1), listOfRecipes.get(2));
//    }
//
//    @Test
//    void saveRecipe() {
//        //given
//        Recipe fourthRecipe = new Recipe("fourth", "fourth recipe", 60, Arrays.asList(new ProductRecipe("fourth", 4.0, ProductUnit.LITR)));
//        fourthRecipe.setRecipeAllegrens(new RecipeAllegrens());
//        //when
//        when(recipeRepositoryMock.save(any(Recipe.class))).thenReturn(fourthRecipe);
//        Recipe recipe = recipeService.saveRecipe(fourthRecipe);
//        //then
//        assertThat(recipe).isEqualTo(fourthRecipe);
//        assertThat(recipe.getProductList().get(0).getUnit()).isEqualTo(ProductUnit.LITR);
//
//    }
//
//    @Test
//    void updateRecipe() {
//        //given
//        Recipe thirdRecipe = new Recipe("third changed", "third changed recipe", 60, List.of(new ProductRecipe("fourth", 4.0, ProductUnit.LITR)));
//        Recipe fourthRecipe = new Recipe("third changed", "third changed recipe", 60, List.of(new ProductRecipe("fourth", 4.0, ProductUnit.LITR)));
//        //when
//        when(recipeRepositoryMock.save(any(Recipe.class))).thenReturn(thirdRecipe);
//        when(recipeRepositoryMock.findById(any())).thenReturn(Optional.of(thirdRecipe));
//        recipeService.updateRecipe(3L, fourthRecipe);
//        //then
//        assertThat(thirdRecipe.getProductList().get(0).getUnit()).isEqualTo(ProductUnit.LITR);
//
//    }
//
//    @Test
//    void getRecipesByMeal(Pageable pageable) {
//
//        //given
//        List<Recipe> listOfRecipes = createListOfRecipes();
//        when(recipeService.getAllRecipe()).thenReturn(listOfRecipes);
//
//
//        //when
//        Page<Recipe> recipesByMeal = recipeService.getRecipesByCanFilterByMeal(Meal.BREAKFAST, pageable);
//
//        //then
//        assertThat(recipesByMeal).hasSize(1).contains(listOfRecipes.get(0));
//    }
//}
