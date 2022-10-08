package com.infoshareacademy.entity.food_preferences;

import javax.persistence.*;

@Entity
public class FoodPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean shellfish;
    private boolean chocolate;
    private boolean nuts;
    private boolean eggs;
    private boolean strawberries;
    private boolean dairy;
    private String other;
    private boolean meatEater;
    private boolean isVegan;
    private boolean isVegetarian;

    public void setId(Long id) {
        this.id = id;
    }

    public FoodPreferences() {
    }

    public FoodPreferences(boolean shellfish, boolean chocolate, boolean nuts, boolean eggs, boolean strawberries, boolean dairy, String other, boolean meatEater, boolean isVegan, boolean isVegetarian) {
        this.shellfish = shellfish;
        this.chocolate = chocolate;
        this.nuts = nuts;
        this.eggs = eggs;
        this.strawberries = strawberries;
        this.dairy = dairy;
        this.other = other;
        this.meatEater = meatEater;
        this.isVegan = isVegan;
        this.isVegetarian = isVegetarian;
    }

    public Long getId() {
        return id;
    }

    public boolean isShellfish() {
        return shellfish;
    }

    public boolean isChocolate() {
        return chocolate;
    }

    public boolean isNuts() {
        return nuts;
    }

    public boolean isEggs() {
        return eggs;
    }

    public boolean isStrawberries() {
        return strawberries;
    }

    public boolean isDairy() {
        return dairy;
    }

    public String getOther() {
        return other;
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

}