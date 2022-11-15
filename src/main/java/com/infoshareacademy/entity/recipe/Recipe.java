package com.infoshareacademy.entity.recipe;

import com.infoshareacademy.entity.product.ProductRecipe;

import com.infoshareacademy.entity.shopping_list.ShoppingList;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", unique = true, updatable = true)
    private Long recipeId;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @Min(1)
    @Max(120)
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

    public Recipe(){

    }

    public List<ProductRecipe> getProductList() {
        return productList;
    }

    public void addProduct(ProductRecipe product) {
        this.productList.add(product);
        product.setRecipe(this);
    }

    public void setProductList(List<ProductRecipe> productList) {
        this.productList = productList;
    }

    public List<ShoppingList> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(List<ShoppingList> shoppingList) {
        this.shoppingList = shoppingList;
    }

    public boolean containsProduct(String s) {
        boolean flag = false;
        for (ProductRecipe product : productList) {
            if (product.getProductName().contains(s)) {
                flag = true;
            }
        }
        return flag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long id) {
        this.recipeId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public RecipeAllergens getRecipeAllergens() {
        return recipeAllergens;
    }


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

    public void setRecipeAllergens(RecipeAllergens recipeAllergens) {
        this.recipeAllergens = recipeAllergens;
    }
}
