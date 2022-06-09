package com.infoshareacademy;

import com.infoshareacademy.food_preferences.FoodPreferencesMethod;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        FoodPreferencesMethod foodPreferencesMethod = new FoodPreferencesMethod();
        foodPreferencesMethod.writeFoodPreferencesToJson();

//        foodPreferencesMethod.setMeatPreferences();
    }

}
