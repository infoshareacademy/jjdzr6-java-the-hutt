package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
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
 * A DTO for the {@link ShoppingList} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingListDto implements Serializable {
    private  Long id;
    private  String name;
    private  List<ProductShoppingListDto> shoppingProductList;
    private  List<RecipeDto> shoppingListRecipe ;
    private  Long userId;

    public void addRecipe(RecipeDto recipe){
        this.shoppingListRecipe.add(recipe);
        recipe.getShoppingList().add(this);
    }

    public void removeRecipe(RecipeDto recipe){
        this.shoppingListRecipe.remove(recipe);
        recipe.getShoppingList().remove(this);
    }

    /**
     * A DTO for the {@link com.infoshareacademy.entity.product.ProductShoppingList} entity
     */
    @Data
    public static class ProductShoppingListDto implements Serializable {
        private  Long productId;
        private  String productName;
        private  Double amount;
        private  ProductUnit unit;
        private ShoppingListDto shoppingListDto;
    }

    /**
     * A DTO for the {@link com.infoshareacademy.entity.recipe.Recipe} entity
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeDto implements Serializable {
        private  Long recipeId;
        @NotEmpty
        private  String name;
        @NotEmpty
        private  String description;
        @Min(1)
        @Max(120)
        private  int preparationTime;
        private  Meal meal;
        private  Long userId;
        private List<ShoppingListDto> shoppingList;
        private List<ProductRecipeDto> productList;
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

    }
}