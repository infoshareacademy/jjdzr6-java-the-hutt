package com.infoshareacademy.fridge;


import java.util.HashMap;
import java.util.Map;

public class Fridge {

private Map <String, Double> productsInFridge = new HashMap<>();

    public Map<String, Double> addFridgeProduct(String name, Double howMany){
        productsInFridge.put(name, howMany);
        return productsInFridge;
    }

    public Fridge() {
    }

    public Map<String, Double> getProductsInFridge() {
        return productsInFridge;
    }

    public void setProductsInFridge(Map<String, Double> productsInFridge) {
        this.productsInFridge = productsInFridge;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "productsInFridge=" + productsInFridge +
                '}';
    }
}
