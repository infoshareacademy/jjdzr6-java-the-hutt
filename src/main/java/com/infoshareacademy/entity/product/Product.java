package com.infoshareacademy.entity.product;

import javax.persistence.*;

@Entity
@Table(name = "my_products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

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
}
