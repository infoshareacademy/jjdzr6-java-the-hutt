package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * A DTO for the {@link ProductRecipe} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecipeDto implements Serializable {
    private  Long productId;
    private  String productName;
    private  Double amount;
    private  ProductUnit unit;
    private  RecipeDto recipe;

    public ProductRecipeDto(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }

    /**
     * A DTO for the {@link Recipe} entity
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
    }

    @Override
    public String toString() {

        return  productName + '\'' +
                ", ilość:" + amount + " " + unit.getValue();
    }
}