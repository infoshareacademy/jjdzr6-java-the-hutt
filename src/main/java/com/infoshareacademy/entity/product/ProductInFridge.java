package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.recipe.Recipe;
import com.infoshareacademy.entity.shopping_list.ShoppingList;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class ProductInFridge extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    public ProductInFridge() {
    }

    public ProductInFridge(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public Fridge getFridge() {
        return fridge;
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
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
