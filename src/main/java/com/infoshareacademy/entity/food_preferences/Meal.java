package com.infoshareacademy.entity.food_preferences;

public enum Meal {
    BREAKFAST("śniadanie"),
    LUNCH("lunch"),
    DINNER("obiad"),
    SUPPER("kolacja");

    private final String value;

    Meal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}