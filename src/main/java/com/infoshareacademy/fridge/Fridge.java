package com.infoshareacademy.fridge;

import com.infoshareacademy.product.*;

import java.util.List;
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
