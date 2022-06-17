package com.infoshareacademy.fridge;

import com.infoshareacademy.product.Product;

import java.util.Map;

public class Fridge {

    private Map<String, Double> productsInFridge;

    public Map<String, Double> getProductInFridge() {
        return productsInFridge;
    }

    public void setProductInFridge(Map<String, Double> productInFridge) {
        this.productsInFridge = productInFridge;
    }

    public Fridge() {
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "productsInFridge=" + productsInFridge +
                '}';
    }
}
