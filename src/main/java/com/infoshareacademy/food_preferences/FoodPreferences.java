package com.infoshareacademy.food_preferences;

import java.util.Scanner;

public class FoodPreferences {
    private AllergenName allergenName;
    private Meat meat;
    Scanner scanner = new Scanner(System.in);;

    public boolean allergicFlag(){
        boolean isAllergic = true;
        String answer = scanner.nextLine();
        if(answer.equalsIgnoreCase("T")){
            isAllergic = true;
        } else {
            isAllergic = false;
        }
        return isAllergic;
    }

    public AllergenName setAllergen() {
        AllergenName allergenName = new AllergenName();
        FoodPreferences foodPreferences = new FoodPreferences();

        System.out.println("Skorupiaki[T/N]: ");
        allergenName.setShellfish(foodPreferences.allergicFlag());
        System.out.println("Czekolada[T/N]:");
        allergenName.setChocolate(foodPreferences.allergicFlag());
        System.out.println("Orzechy[T/N]:");
        allergenName.setNuts(foodPreferences.allergicFlag());
        System.out.println("Jajka[T/N]:");
        allergenName.setEggs(foodPreferences.allergicFlag());
        System.out.println("Truskawki[T/N]:");
        allergenName.setStrawberries(foodPreferences.allergicFlag());
        System.out.println("Produkty mleczne[T/N]:");
        allergenName.setDairy(foodPreferences.allergicFlag());
        System.out.println("Inne alergie (podaj): ");
        allergenName.setOther(scanner.nextLine());
        return allergenName;
    }
}
