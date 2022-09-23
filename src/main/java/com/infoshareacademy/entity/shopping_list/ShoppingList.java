package com.infoshareacademy.entity.shopping_list;
import com.infoshareacademy.entity.product.ProductShoppingList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "shopping_list_id")
    private Long id;
    @OneToMany(mappedBy = "shoppingList",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductShoppingList> shoppingProductList = new ArrayList<>();

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

    @Override
    public String toString() {
        return "ShoppingList{" +
                "id=" + id +
                ", shoppingProductList=" + shoppingProductList +
                '}';
    }
}