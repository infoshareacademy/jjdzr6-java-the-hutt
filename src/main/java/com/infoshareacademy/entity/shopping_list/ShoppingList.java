package com.infoshareacademy.entity.shopping_list;

import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.recipe.Recipe;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_list_id")
    private Long id;

    private String name;
    @OneToMany(mappedBy = "shoppingList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductShoppingList> shoppingProductList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinTable(name = "shopping_recipe_list",
            joinColumns = @JoinColumn(name = "shooping_list_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private List<Recipe> shoppingListRecipe = new ArrayList<>();
    private Long userId;

    public ShoppingList() {
    }

    public Long getId() {
        return id;
    }

    public List<ProductShoppingList> getShoppingProductList() {
        return shoppingProductList;
    }

    public void setShoppingProductList(List<ProductShoppingList> shoppingProductList) {
        this.shoppingProductList = shoppingProductList;
    }

    public void addRecipe(Recipe recipe) {
        this.shoppingListRecipe.add(recipe);
        recipe.getShoppingList().add(this);
    }

    public void setId(Long id) {
        this.id = id;
    }



    public void removeRecipe(Recipe recipe) {
        this.shoppingListRecipe.remove(recipe);
        recipe.getShoppingList().remove(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Recipe> getShoppingListRecipe() {
        return shoppingListRecipe;
    }

    public void setShoppingListRecipe(List<Recipe> shoppingListRecipe) {
        this.shoppingListRecipe = shoppingListRecipe;
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", shoppingProductList=" + shoppingProductList +
                '}';
    }


}