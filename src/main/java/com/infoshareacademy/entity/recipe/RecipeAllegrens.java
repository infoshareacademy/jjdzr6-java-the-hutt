package com.infoshareacademy.entity.recipe;

import javax.persistence.*;

@Entity
public class RecipeAllegrens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean chocolate;
    private boolean nuts;
    private boolean eggs;
    private boolean strawberries;
    private boolean shellfish;
    private boolean dairy;
    private String other;
    private boolean meatEater;
    private boolean Vegan;
    private boolean Vegetarian;

    @JoinColumn(name = "recipe_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Recipe recipe;

    public RecipeAllegrens() {
    }

    public RecipeAllegrens(boolean chocolate, boolean nuts, boolean eggs, boolean strawberries, boolean shellfish, boolean dairy, String other, boolean meatEater, boolean vegan, boolean vegetarian) {
        this.chocolate = chocolate;
        this.nuts = nuts;
        this.eggs = eggs;
        this.strawberries = strawberries;
        this.shellfish = shellfish;
        this.dairy = dairy;
        this.other = other;
        this.meatEater = meatEater;
        Vegan = vegan;
        Vegetarian = vegetarian;
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
        return Vegan;
    }

    public boolean isVegetarian() {
        return Vegetarian;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
