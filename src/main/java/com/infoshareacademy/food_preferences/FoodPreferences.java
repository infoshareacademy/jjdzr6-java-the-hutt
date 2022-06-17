package com.infoshareacademy.food_preferences;

public class FoodPreferences {
    private AllergenName allergenName;
    private Meat meat;

    public FoodPreferences() {
    }

    @Override
    public String toString() {
        return "FoodPreferences{" +
                "allergenName=" + allergenName +
                ", meat=" + meat +
                '}';
    }

    public void setAllergenName(AllergenName allergenName) {
        this.allergenName = allergenName;
    }

    public void setMeat(Meat meat) {
        this.meat = meat;
    }

    public AllergenName getAllergenName() {
        return allergenName;
    }

    public Meat getMeat() {
        return meat;
    }
}
