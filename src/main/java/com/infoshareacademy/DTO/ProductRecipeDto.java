package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecipeDto implements Serializable {
    private Long productId;
    @NotBlank(message = "Nazwa produktu nie może być pusta!")
    private String productName;
    @NotNull(message = "Ilość musi być większa od zera!")
    private Double amount;
    private ProductUnit unit;
    private RecipeDto recipe;

    public ProductRecipeDto(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeDto implements Serializable {
        private Long recipeId;
        @NotEmpty(message = "Pole \"tytuł\" nie może być puste!")
        private String name;
        @NotEmpty(message = "Pole \"opis\" nie może być puste!")
        private String description;
        @NotNull(message = "Czas przygotowania nie może być pusty!")
        @Min(value = 1, message = "Czas przygotowania nie może być krótszy niż minuta!")
        @Max(value = 120, message = "Czas przygotowania nie może przekroczyć 120 minut!")
        private int preparationTime;
        private Meal meal;
        private Long userId;
    }

    @Override
    public String toString() {

        return productName + '\'' +
                ", ilość:" + amount + " " + unit.getValue();
    }
}