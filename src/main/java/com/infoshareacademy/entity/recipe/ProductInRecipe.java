package com.infoshareacademy.entity.recipe;


import com.infoshareacademy.entity.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "recipe_products")
public class ProductInRecipe extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productInRecipeId;

    public ProductInRecipe() {
        super();
    }

    public ProductInRecipe(String productName, Double amount){
        super(productName, amount);
    }

    public Long getProductInRecipeId() {
        return productInRecipeId;
    }
}
