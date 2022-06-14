package com.infoshareacademy.service.shopping_list;

import java.util.Map;

public class ShoppingList {

    private Map<String, Object> productList;


    public Map<String, Object> getProductList() {
        return productList;
    }

    public void setProductList(Map<String, Object> productList) {
        this.productList = productList;
    }
}
