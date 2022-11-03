package com.infoshareacademy.entity.shopping_list;

import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.product.ProductShoppingList;
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
    @OneToMany(mappedBy = "shoppingList", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<ProductShoppingList> shoppingProductList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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