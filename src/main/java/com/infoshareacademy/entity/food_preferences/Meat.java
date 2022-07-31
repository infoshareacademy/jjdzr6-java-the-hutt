package com.infoshareacademy.entity.food_preferences;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Meat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean meatEater;
    private boolean isVegan;
    private boolean isVegetarian;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isMeatEater() {
        return meatEater;
    }

    public void setMeatEater(boolean meatEater) {
        this.meatEater = meatEater;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    @Override
    public String toString() {
        return "\n preferuje mięso: " + meatEater +
                "\n weganin: " + isVegan +
                "\n wegetarianin: " + isVegetarian;
    }
}
