package com.infoshareacademy.food_preferences;

public class FoodPreferences {
    private Taste taste;
    private AllergenName allergenName;
    private Meat meat;
    private Meal meal;

    public FoodPreferences() {
    }

    @Override
    public String toString() {
        return "FoodPreferences{" +
                "taste=" + taste +
                ", allergenName=" + allergenName +
                ", meat=" + meat +
                ", meal=" + meal +
                '}';
    }

//    public void setTastePreferences() {
//        if (getTaste().equals("słodki")) {
//
//        } else if (getTaste().equals("kwaśny")) {
//
//        } else if (getTaste().equals("słony")) {
//
//        } else if (getTaste().equals("gorzki")) {
//
//        }
//    }
}
