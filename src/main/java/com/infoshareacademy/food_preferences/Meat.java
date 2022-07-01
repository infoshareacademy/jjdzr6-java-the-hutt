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

    public boolean isMeatEater() {
        return meatEater;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    @Override
    public String toString() {
        return "\n preferuje mięso: " + meatEater +
                "\n weganin: " + isVegan +
                "\n wegetarianin: " + isVegetarian;
    }
}
