package com.infoshareacademy.shopping_list;

import java.util.Map;

public class ShoppingList {

    private Map<String, Object> productList;


    public Map<String, Object> getProductList() {
        return productList;
    }

    public ShoppingList(Map<String, Object> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "productList=" + productList +
                '}';
    }
}
