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

    public void setShellfish(boolean shellfish) {
        this.shellfish = shellfish;
    }

    public void setChocolate(boolean chocolate) {
        this.chocolate = chocolate;
    }

    public void setNuts(boolean nuts) {
        this.nuts = nuts;
    }

    public void setEggs(boolean eggs) {
        this.eggs = eggs;
    }

    public void setStrawberries(boolean strawberries) {
        this.strawberries = strawberries;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void setMeatEater(boolean meatEater) {
        this.meatEater = meatEater;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
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