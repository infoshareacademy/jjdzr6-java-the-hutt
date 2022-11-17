package com.infoshareacademy.entity.shopping_list;

import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public void addRecipe(Recipe recipe) {
        this.shoppingListRecipe.add(recipe);
        recipe.getShoppingList().add(this);
    }

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", shoppingProductList=" + shoppingProductList +
                '}';
    }
}