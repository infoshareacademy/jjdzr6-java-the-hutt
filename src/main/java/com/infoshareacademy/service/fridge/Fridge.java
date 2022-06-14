package com.infoshareacademy.service.fridge;

import com.infoshareacademy.product.*;
import com.infoshareacademy.service.product.Product;

import java.util.Map;

public class Fridge {

    private Map<Product, Double> productInFridge;

    public Map<Product, Double> getProductInFridge() {
        return productInFridge;
    }

    public void setProductInFridge(Map<Product, Double> productInFridge) {
        this.productInFridge = productInFridge;
    }
}
