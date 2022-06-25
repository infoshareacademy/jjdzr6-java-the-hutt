package com.infoshareacademy.recipe;


import java.util.Map;

public class Recipe {

    private String name;
    private String description;
    private int preparationTime;
    private Map<String, Double> neccesaryProducts = new HashMap<>();

    public Map<String, Double> addNecessaryProducts(String name, Double howMany){
        neccesaryProducts.put(name, howMany);
        return neccesaryProducts;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Double> getNeccesaryProducts() {
        return neccesaryProducts;
    }

    public void setNeccesaryProducts(Map<String, Double> neccesaryProducts) {
        this.neccesaryProducts = neccesaryProducts;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Recipe() {
    }

    @Override
    public String toString() {
        return "Przepis na: " +
                  name +
                "\n Opis: " + description  +
                ", Niezbędne produkty: " + neccesaryProducts +
                ", czas przygotowania: [min] " + preparationTime;
    }

}
