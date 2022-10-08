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
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isShellfish() {
        return shellfish;
    }

    public void setShellfish(boolean shellfish) {
        this.shellfish = shellfish;
    }

    public boolean isChocolate() {
        return chocolate;
    }

    public void setChocolate(boolean chocolate) {
        this.chocolate = chocolate;
    }

    public boolean isNuts() {
        return nuts;
    }

    public void setNuts(boolean nuts) {
        this.nuts = nuts;
    }

    public boolean isEggs() {
        return eggs;
    }

    public void setEggs(boolean eggs) {
        this.eggs = eggs;
    }

    public boolean isStrawberries() {
        return strawberries;
    }

    public void setStrawberries(boolean strawberries) {
        this.strawberries = strawberries;
    }

    public boolean isDairy() {
        return dairy;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public boolean isMeatEater() {
        return meatEater;
    }

    public void setMeatEater(boolean meatEater) {
        this.meatEater = meatEater;
    }

    public boolean isVegan() {
        return Vegan;
    }

    public void setVegan(boolean vegan) {
        Vegan = vegan;
    }

    public boolean isVegetarian() {
        return Vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        Vegetarian = vegetarian;
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
