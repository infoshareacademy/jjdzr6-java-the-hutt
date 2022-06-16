package com.infoshareacademy.recipe;

import com.infoshareacademy.product.Product;
import com.infoshareacademy.food_preferences.FoodPreferences;

import java.util.List;
import java.util.Map;

public class Recipe {

    private String name;
    private String description;
    private Map<String, Double> neccesaryProducts;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Double> getNeccesaryProducts() {
        return neccesaryProducts;
    }

    public void setNeccesaryProducts(Map<String, Double> neccesaryProducts) {
        this.neccesaryProducts = neccesaryProducts;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public List<FoodPreferences> getMealPreferences() {
        return mealPreferences;
    }

    public void setMealPreferences(List<FoodPreferences> mealPreferences) {
        this.mealPreferences = mealPreferences;
    }

    private int preparationTime;
    private List<FoodPreferences> mealPreferences;

    public Recipe() {
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", neccesaryProducts=" + neccesaryProducts +
                ", preparationTime=" + preparationTime +
                ", mealPreferences=" + mealPreferences +
                '}';
    }
}
