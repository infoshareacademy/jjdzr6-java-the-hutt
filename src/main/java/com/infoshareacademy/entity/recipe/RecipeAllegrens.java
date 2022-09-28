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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Recipe recipe;

    public RecipeAllegrens() {
    }


    public RecipeAllegrens(Recipe recipe) {
        setRecipe(recipe);
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "RecipeAllegrens{" +
                "id=" + id +
                ", shellfish=" + shellfish +
                ", chocolate=" + chocolate +
                ", nuts=" + nuts +
                ", eggs=" + eggs +
                ", strawberries=" + strawberries +
                ", dairy=" + dairy +
                ", other='" + other + '\'' +
                ", meatEater=" + meatEater +
                ", Vegan=" + Vegan +
                ", Vegetarian=" + Vegetarian +
                ", recipe=" + recipe +
                '}';
    }
}
