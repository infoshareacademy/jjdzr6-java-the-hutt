package com.infoshareacademy.fridge;

import com.infoshareacademy.product.*;

import java.util.List;
import java.util.Map;

public class Fridge {

    private Map<String, Double> productsInFridge;

    public Map<String, Double> getProductInFridge() {
        return productsInFridge;
    }

    public void setProductInFridge(Map<String, Double> productInFridge) {
        this.productsInFridge = productInFridge;
    }
}
