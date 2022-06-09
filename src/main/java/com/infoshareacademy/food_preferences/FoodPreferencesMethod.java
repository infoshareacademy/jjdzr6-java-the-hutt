package com.infoshareacademy.food_preferences;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.Scanner;

public class FoodPreferencesMethod {
    Scanner scanner = new Scanner(System.in);
    boolean run;
    public boolean preferencesFlag() {
        boolean yesPreferences = false;
        run = false;

        do {
            try{
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase("T")) {
                yesPreferences = true;
                run = true;
            } else if (answer.equalsIgnoreCase("N")) {
                yesPreferences = false;
                run = true;
            } else {
                System.out.println("Błędny format odpowiedzi. Wprowadź odpowiedź ponownie.");
                }
            } catch (InputMismatchException exception){
                System.out.println("Błędny format odpowiedzi. Wprowadź odpowiedź ponownie.");
            }
        } while (!run);
        return yesPreferences;
    }

    public AllergenName setAllergenPreferences() {
        AllergenName allergenName = new AllergenName();


        System.out.println("Skorupiaki[T/N]: ");
        allergenName.setShellfish(preferencesFlag());
        System.out.println("Czekolada[T/N]:");
        allergenName.setChocolate(preferencesFlag());
        System.out.println("Orzechy[T/N]:");
        allergenName.setNuts(preferencesFlag());
        System.out.println("Jajka[T/N]:");
        allergenName.setEggs(preferencesFlag());
        System.out.println("Truskawki[T/N]:");
        allergenName.setStrawberries(preferencesFlag());
        System.out.println("Produkty mleczne[T/N]:");
        allergenName.setDairy(preferencesFlag());
        System.out.println("Inne alergie (podaj): ");
        allergenName.setOther(scanner.nextLine());

        System.out.println(allergenName);
        return allergenName;
    }

    public Meat setMeatPreferences() {
        Meat meat = new Meat();
        boolean flagMeat = false;

        System.out.println("Mięso[T/N]: ");
        meat.setMeatEat(preferencesFlag());
        System.out.println("Dieta Wegańska[T/N]: ");
        meat.setVegan(preferencesFlag());
        System.out.println("Dieta Wegetariańska[T/N]: ");
        meat.setVegetarian(preferencesFlag());

        System.out.println(meat);
        return meat;
    }

    public void writeFoodPreferencesToJson() throws IOException {
        Path path = Path.of("src", "resources", "food_preferences.json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        FoodPreferences foodPreferences = new FoodPreferences();
        AllergenName allergenName = setAllergenPreferences();
        foodPreferences.setAllergenName(allergenName);
        Meat meat = setMeatPreferences();
        foodPreferences.setMeat(meat);

        String foodPreferencesJson = objectWriter.writeValueAsString(foodPreferences);
        Files.writeString(path, foodPreferencesJson);
    }


}
