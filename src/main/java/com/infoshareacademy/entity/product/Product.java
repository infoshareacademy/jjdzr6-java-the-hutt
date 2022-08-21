package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.recipe.Recipe;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public Product() {
    }

    public Product(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
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
        return "Składniki: " +
                " nazwa składnika='" + productName + '\'' +
                ", ilość=" + amount;
    }
}
