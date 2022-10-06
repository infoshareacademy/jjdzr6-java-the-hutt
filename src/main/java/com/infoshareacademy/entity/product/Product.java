package com.infoshareacademy.entity.product;

public abstract class Product {

    private String productName;

    private Double amount;

    private ProductUnit unit;

    public Product(String productName, Double amount, ProductUnit unit) {
        this.productName = productName;
        this.amount = amount;
        this.unit = unit;
    }

    public Product() {

    }

    public String getProductName() {
        return productName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}