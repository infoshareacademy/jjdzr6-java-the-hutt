package com.infoshareacademy.entity.recipe;


import com.vladmihalcea.hibernate.type.json.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@TypeDef(name = "json", typeClass = JsonType.class)
@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "preparation_time")
    private int preparationTime;

    @ElementCollection
    @CollectionTable(name = "neccessary_products_mapping",
            joinColumns = {@JoinColumn(name = "recipe_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "name")
    @Column(name = "howMany")
//    @Type(type = "json")
//    @Column(name = "neccesaryProducts", columnDefinition = "jsonb")
    private Map<String, Double> neccesaryProducts = new HashMap<>();
    public Map<String, Double> addNecessaryProducts(String name, Double howMany){
        neccesaryProducts.put(name, howMany);
        return neccesaryProducts;
    }

    public Recipe() {
    }

    public Recipe(String name, String description, int preparationTime, Map<String, Double> neccesaryProducts) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.neccesaryProducts = neccesaryProducts;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Przepis na: " +
                  name +
                "\n Opis: " + description  +
                ", Niezbędne produkty: " + neccesaryProducts +
                ", czas przygotowania: [min] " + preparationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return preparationTime == recipe.preparationTime && Objects.equals(name, recipe.name) && Objects.equals(description, recipe.description) && Objects.equals(neccesaryProducts, recipe.neccesaryProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, preparationTime, neccesaryProducts);
    }
}
