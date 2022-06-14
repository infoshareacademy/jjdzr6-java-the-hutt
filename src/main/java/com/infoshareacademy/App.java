package com.infoshareacademy;

import com.infoshareacademy.service.FoodPreferencesService;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        FoodPreferencesService foodPreferencesService = new FoodPreferencesService();
        foodPreferencesService.writeFoodPreferencesToJson();

//        foodPreferencesMethod.setMeatPreferences();
    }

}
