package com.infoshareacademy.entity.recipe;

import com.infoshareacademy.entity.product.ProductRecipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", unique = true, updatable = true)
    private Long recipeId;

    @NotEmpty(message = "Pole \"tytuł\" nie może być puste!")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Pole \"opis\" nie może być puste!")
    @Column(name = "description")
    private String description;

    @Min(value = 1, message = "Czas przygotowania nie może być krótszy niż minuta!")
    @Max(value = 120, message = "Czas przygotowania nie może przekroczyć 120 minut!")
    @NotNull(message = "Czas przygotowania nie może być pusty!")
    @Column(name = "preparation_time")
    private int preparationTime;

    @Column
    @Enumerated(EnumType.STRING)
    private Meal meal;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductRecipe> productList = new ArrayList<>();

    @ManyToMany(mappedBy = "shoppingListRecipe", cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    private List<ShoppingList> shoppingList = new ArrayList<>();

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RecipeAllergens recipeAllergens;

    private Long userId;

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + recipeId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", preparationTime=" + preparationTime +
                ", productList=" + productList +
                '}';
    }

}
