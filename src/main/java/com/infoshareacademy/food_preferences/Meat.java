package com.infoshareacademy.food_preferences;

public class Meat {
    private boolean meatEater;
    private boolean isVegan;
    private boolean isVegetarian;

    public void setMeatEater(boolean meatEater) {
        this.meatEater = meatEater;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    @Override
    public String toString() {
        return "Meat{" +
                "meatEater=" + meatEater +
                ", isVegan=" + isVegan +
                ", isVegetarian=" + isVegetarian +
                '}';
    }
}
