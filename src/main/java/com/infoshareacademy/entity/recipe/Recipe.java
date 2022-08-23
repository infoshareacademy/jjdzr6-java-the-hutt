package com.infoshareacademy.entity.recipe;


import com.infoshareacademy.entity.product.Product;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "recipes")

public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "preparation_time")
    private int preparationTime;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productList = new ArrayList<>();

    public Recipe() {
    }



    public List<Product> getProductList() {
        return productList;
    }

    public void addProduct(Product product) {
        this.productList.add(product);
        product.setRecipe(this);
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
