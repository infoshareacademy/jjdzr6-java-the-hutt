package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;

import javax.persistence.*;

@Entity
@Table(name = "products_shopping_list")
public class ProductShoppingList extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;

    public ProductShoppingList() {
    }

    public ProductShoppingList(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }



    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double quantity) {
        this.amount = quantity;
    }

    @Override
    public String toString() {
        return "nazwa składnika='" + productName + '\'' +
                ", ilość=" + amount + "\'";
    }
}
