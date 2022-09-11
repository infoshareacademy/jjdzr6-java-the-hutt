package com.infoshareacademy.entity.shopping_list;

import com.infoshareacademy.entity.product.Product;

import javax.persistence.*;

@Entity
@Table(name = "shoppinglist_products")
public class ProductInShoppingList extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productInRecipeId;

    public ProductInShoppingList() {
        super();
    }

    public ProductInShoppingList(String productName, Double amount){
        super(productName, amount);
    }

    public Long getProductInRecipeId() {
        return productInRecipeId;
    }
}
