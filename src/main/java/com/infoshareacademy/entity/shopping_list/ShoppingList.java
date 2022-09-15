package com.infoshareacademy.entity.shopping_list;

import com.infoshareacademy.entity.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "shopping_list_id")
    private Long id;
    @OneToMany(mappedBy = "shoppingList",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> shoppingProductList = new ArrayList<>();

    public ShoppingList() {
    }

    public Long getId() {
        return id;
    }

    public List<Product> getShoppingProductList() {
        return shoppingProductList;
    }

    public void setShoppingProductList(List<Product> shoppingProductList) {
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
