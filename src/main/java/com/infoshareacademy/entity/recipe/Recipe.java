package com.infoshareacademy.entity.recipe;


import com.infoshareacademy.entity.product.ProductRecipe;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "recipe_id")
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

    @OneToOne(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RecipeAllegrens recipeAllegrens;

    public Recipe() {
    }

    public Recipe(String name, String description, int preparationTime, List<ProductRecipe> productList) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.productList = productList;
    }

    public List<ProductRecipe> getProductList() {
        return productList;
    }

    public void addProduct(ProductRecipe product) {
        this.productList.add(product);
        product.setRecipe(this);
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

    public RecipeAllegrens getRecipeAllegrens() {
        return recipeAllegrens;
    }

    public void setRecipeAllegrens(RecipeAllegrens recipeAllegrens) {
        this.recipeAllegrens = recipeAllegrens;
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
}
