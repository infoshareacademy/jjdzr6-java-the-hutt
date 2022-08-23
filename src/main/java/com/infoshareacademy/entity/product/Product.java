package com.infoshareacademy.entity.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infoshareacademy.entity.recipe.Recipe;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public Product() {
    }

    public Product(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double quantity) {
        this.amount = quantity;
    }

    @Override
    public String toString() {
        return "nazwa składnika='" + productName + '\'' +
                ", ilość=" + amount +"\'";
    }
}
