package com.infoshareacademy.food_preferences;

import java.util.Scanner;

public class FoodPreferences {
    private AllergenName allergenName;
    private Meat meat;
    Scanner scanner = new Scanner(System.in);
    ;

    public boolean preferencesFlag() {
        boolean yesPreferences = true;
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("T")) {
            yesPreferences = true;
        } else {
            yesPreferences = false;
        }
        return yesPreferences;
    }

    public AllergenName setAllergen() {
        AllergenName allergenName = new AllergenName();
        FoodPreferences foodPreferences = new FoodPreferences();

        System.out.println("Skorupiaki[T/N]: ");
        allergenName.setShellfish(foodPreferences.preferencesFlag());
        System.out.println("Czekolada[T/N]:");
        allergenName.setChocolate(foodPreferences.preferencesFlag());
        System.out.println("Orzechy[T/N]:");
        allergenName.setNuts(foodPreferences.preferencesFlag());
        System.out.println("Jajka[T/N]:");
        allergenName.setEggs(foodPreferences.preferencesFlag());
        System.out.println("Truskawki[T/N]:");
        allergenName.setStrawberries(foodPreferences.preferencesFlag());
        System.out.println("Produkty mleczne[T/N]:");
        allergenName.setDairy(foodPreferences.preferencesFlag());
        System.out.println("Inne alergie (podaj): ");
        allergenName.setOther(scanner.nextLine());
        return allergenName;
    }

    public Meat setMeat() {
        Meat meat = new Meat();
        FoodPreferences foodPreferences = new FoodPreferences();

        System.out.println("Mięso[T/N]: ");
        meat.setMeatEat(foodPreferences.preferencesFlag());
        System.out.println("Dieta Wegetariańska[T/N]: ");
        meat.setVegetarian(foodPreferences.preferencesFlag());
        System.out.println("Dieta Wegańska[T/N]: ");
        meat.setVegan(foodPreferences.preferencesFlag());

        return meat;
    }

}
