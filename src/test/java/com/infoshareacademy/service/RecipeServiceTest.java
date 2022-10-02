package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {


    private final ProductRecipe firstProduct = new ProductRecipe("first product", 1.0, ProductUnit.GRAM);
    private final ProductRecipe secondProduct = new ProductRecipe("second product", 2.0, ProductUnit.LITR);
    private final ProductRecipe thirdProduct = new ProductRecipe("third product", 3.0, ProductUnit.KILOGRAM);

    private final Recipe firstRecipe = new Recipe("first", "first recipe", 15, Arrays.asList(firstProduct));
    private final Recipe secondRecipe = new Recipe("second", "second recipe", 30, Arrays.asList(firstProduct, secondProduct));
    private final Recipe thirdRecipe = new Recipe("third", "third recipe", 45, Arrays.asList(firstProduct, secondProduct, thirdProduct));

    RecipeRepository recipeRepositoryMock = mock(RecipeRepository.class);
    private RecipeService recipeService = new RecipeService(recipeRepositoryMock);


    @Test
    void getAllRecipe() {
        //given
        when(recipeService.getAllRecipe()).thenReturn(Arrays.asList(firstRecipe, secondRecipe, thirdRecipe));

        //when
        List<Recipe> allRecipe = recipeService.getAllRecipe();

        //then
        assertThat(allRecipe).hasSize(3)
                .containsExactly(firstRecipe, secondRecipe, thirdRecipe);
    }

    @Test
    void saveRecipe() {
        //given
        Recipe fourthRecipe = new Recipe("fourth", "fourth recipe", 60, Arrays.asList(new ProductRecipe("fourth", 4.0, ProductUnit.LITR)));
        //when
        when(recipeRepositoryMock.save(any(Recipe.class))).thenReturn(fourthRecipe);
        Recipe recipe = recipeService.saveRecipe(fourthRecipe);
        //then
        assertThat(recipe).isEqualTo(fourthRecipe);
        assertThat(recipe.getProductList().get(0).getUnit().compareTo(ProductUnit.LITR));

    }

    @Test
    void updateRecipe() {
        //given
        thirdRecipe.setRecipeId(3L);
        Recipe fourthRecipe = new Recipe("third changed", "third changed recipe", 60, Arrays.asList(new ProductRecipe("fourth", 4.0, ProductUnit.LITR)));
        //when
        when(recipeRepositoryMock.save(any(Recipe.class))).thenReturn(thirdRecipe);
        when(recipeRepositoryMock.findById(any())).thenReturn(Optional.of(thirdRecipe));
        recipeService.updateRecipe(3L, fourthRecipe);
        //then
        assertThat(thirdRecipe.getProductList().get(0).getUnit().compareTo(ProductUnit.LITR));

    }
}