package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Comparator;
import java.util.List;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    RecipeRepository recipeRepository = mock(RecipeRepository.class);
    RecipeService recipeService = new RecipeService(recipeRepository);

    @BeforeEach
    public void setUp() {
        Recipe recipe1 = new Recipe();
        recipe1.setRecipeId(1L);
        recipe1.setName("Jajecznica");
        recipe1.setDescription("Pyszna jajecznica");
        recipe1.setPreparationTime(1);
        ProductRecipe product1a = new ProductRecipe("jajka", 1.0);
        ProductRecipe product1b = new ProductRecipe("olej", 2.0);
        recipe1.addProduct(product1a);
        recipe1.addProduct(product1b);
        System.out.println(recipe1);
        recipeService.saveRecipe(recipe1);
        recipeService.getAllRecipe();

        Recipe recipe2 = new Recipe();
        recipe2.setRecipeId(2L);
        recipe2.setName("Kakao");
        recipe2.setDescription("Kakao mniam mniam");
        recipe2.setPreparationTime(10);
        ProductRecipe product2a = new ProductRecipe("kakao", 1.0);
        recipe2.addProduct(product2a);
        recipeService.saveRecipe(recipe2);
        recipeService.getAllRecipe();

        Recipe recipe3 = new Recipe();
        recipe3.setRecipeId(3L);
        recipe3.setName("Tosty");
        recipe3.setDescription("Tosty z chlebem");
        recipe3.setPreparationTime(5);
        ProductRecipe product3a = new ProductRecipe("chleb", 5.0);
        ProductRecipe product3b = new ProductRecipe("sznyka", 2.0);
        ProductRecipe product3c = new ProductRecipe("ser", 1.5);
        recipe3.addProduct(product3a);
        recipe3.addProduct(product3b);
        recipe3.addProduct(product3c);
        recipeService.saveRecipe(recipe3);
        recipeService.getAllRecipe();

    }
//TODO Fix empty list
    @Test
    void sortByPreparationTimeAsc() {
        //given
        Recipe testRecipe = new Recipe();
        testRecipe.setPreparationTime(1);
        //when
        List<Recipe> recipesAsc = recipeService.sortByPreparationTimeAsc();
        //then
        Assertions.assertThat(recipesAsc)
                .hasSize(3)
                .isSortedAccordingTo(Comparator.comparing(Recipe::getPreparationTime));
        assertEquals(testRecipe.getPreparationTime(), recipeService.getAllRecipe().get(0).getPreparationTime());

//        recipeService.getAllRecipe();
//        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
//        System.out.println(recipeService.sortByPreparationTimeAsc());
    }

    @Test
    void sortByPreparationTimeDesc() {
        System.out.println(recipeService.sortByPreparationTimeDesc());
    }
}