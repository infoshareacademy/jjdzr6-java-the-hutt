package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.shopping_list.ShoppingList;
import javax.persistence.*;

@Entity
@Table(name = "products_shopping_list")
public class ProductShoppingList extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
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

    public ProductUnit getUnit() {
        return unit;
    }

    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "ProductShoppingList{" +
                "productName='" + productName + '\'' +
                ", amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
