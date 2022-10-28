package com.infoshareacademy.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodPreferencesDto implements Serializable {
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
}