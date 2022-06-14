package com.infoshareacademy.service.recipe;

import com.infoshareacademy.food_preferences.*;
import com.infoshareacademy.service.food_preferences.FoodPreferences;
import com.infoshareacademy.service.product.Product;

import java.util.List;
import java.util.Map;

public class Recipe {

    private String name;
    private String description;
    private Map<Product, Double> neccesaryProducts;
    private int preparationTime;
    private List<FoodPreferences> mealPreferences;


}
