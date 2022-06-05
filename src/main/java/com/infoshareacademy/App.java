package com.infoshareacademy;

import com.infoshareacademy.food_preferences.AllergenName;
import com.infoshareacademy.food_preferences.FoodPreferences;

public class App
{
    public static void main( String[] args )
    {
//        System.out.println( "Team name: Java The Hutt" );
//        Menu menu = new Menu();
//        menu.mainMenu();

        AllergenName allergenName = new AllergenName();
        FoodPreferences foodPreferences = new FoodPreferences();

        System.out.println(foodPreferences.setMeat());
    }

}
