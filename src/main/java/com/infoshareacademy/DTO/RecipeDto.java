package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.recipe.Meal;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.recipe.RecipeAllergens;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
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
    @NotEmpty(message = "Pole \"tytuł\" nie może być puste!")
    private String name;
    @NotEmpty(message = "Pole \"opis\" nie może być puste!")
    private String description;
    @NotNull(message = "Czas przygotowania nie może być pusty!")
    @Min(value = 1, message = "Czas przygotowania nie może być krótszy niż minuta!")
    @Max(value = 120, message = "Czas przygotowania nie może przekroczyć 120 minut!")
    private int preparationTime;
    private Meal meal;
    @Valid
    private List<ProductRecipeDto> productList = new ArrayList<>();
    private List<ShoppingListDto> shoppingList;
    @Valid
    private RecipeAllergensDto recipeAllergens;
    private Long userId;

    public void addProduct(ProductRecipeDto product) {
        if (productList == null) {
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
        @NotBlank(message = "Nazwa produktu nie może być pusta!")
        private String productName;
        @NotNull(message = "Ilość musi być większa od zera!")
        private Double amount;
        private ProductUnit unit;
        private RecipeDto recipeDto;

        @Override
        public String toString() {

            return productName + '\'' +
                    ", ilość:" + amount + " " + unit.getValue();
        }


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
     * A DTO for the {@link RecipeAllergens} entity
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecipeAllergensDto implements Serializable {
        private Long id;
        private boolean chocolate;
        private boolean nuts;
        private boolean eggs;
        private boolean strawberries;
        private boolean shellfish;
        private boolean dairy;
        @Pattern(regexp = "^[A-Za-z]*$", message = "Pole \"Inne alergeny\" musi być tekstem!")
        private String other;
        private boolean meatEater;
        private boolean isVegan;
        private boolean isVegetarian;
        private RecipeDto recipeDto;
    }
}