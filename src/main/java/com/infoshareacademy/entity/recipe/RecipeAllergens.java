package com.infoshareacademy.entity.recipe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeAllergens {

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
    private boolean isVegan;
    private boolean isVegetarian;

    @JoinColumn(name = "recipe_id")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Recipe recipe;

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
                ", Vegan=" + isVegan +
                ", Vegetarian=" + isVegetarian +
                ", recipe=" + recipe +
                '}';
    }
}
