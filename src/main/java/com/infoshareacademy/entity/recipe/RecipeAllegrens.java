package com.infoshareacademy.entity.recipe;

import javax.persistence.*;

@Entity
public class RecipeAllegrens {

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
    private boolean Vegan;
    private boolean Vegetarian;

    @JoinColumn(name = "recipe_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Recipe recipe;

    public RecipeAllegrens() {
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
