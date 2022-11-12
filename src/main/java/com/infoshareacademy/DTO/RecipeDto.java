package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllegrens;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link Recipe} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto implements Serializable {
    private Long recipeId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Min(1)
    @Max(120)
    private int preparationTime;
    private Meal meal;
    private List<ProductRecipeDto> productList;
    private List<ShoppingListDto> shoppingList;
    private RecipeAllegrensDto recipeAllegrens;
    private Long userId;

    public void addProduct(ProductRecipeDto product) {
        if(productList == null){
            productList = new ArrayList<>();
        }
        this.productList.add(product);
        product.setRecipeDto(this);
    }

    /**
     * A DTO for the {@link com.infoshareacademy.entity.product.ProductRecipe} entity
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductRecipeDto implements Serializable {
        private Long productId;
        private String productName;
        private Double amount;
        private ProductUnit unit;
        private RecipeDto recipeDto;


    }

    /**
     * A DTO for the {@link com.infoshareacademy.entity.shopping_list.ShoppingList} entity
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShoppingListDto implements Serializable {
        private Long id;
        private String name;
        private Long userId;
        private RecipeDto recipeDto;
    }

    /**
     * A DTO for the {@link RecipeAllegrens} entity
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeAllegrensDto implements Serializable {
        private Long id;
        private boolean chocolate;
        private boolean nuts;
        private boolean eggs;
        private boolean strawberries;
        private boolean shellfish;
        private boolean dairy;
        private String other;
        private boolean meatEater;
        private boolean Vegan;
        private boolean Vegetarian;
        private RecipeDto recipeDto;
    }
}