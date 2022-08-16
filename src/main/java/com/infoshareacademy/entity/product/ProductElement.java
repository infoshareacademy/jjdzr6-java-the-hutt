package com.infoshareacademy.entity.product;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products")
public class ProductElement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

    public ProductElement() {
    }

    public ProductElement(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductElement productElement = (ProductElement) o;
        return Objects.equals(id, productElement.id) && Objects.equals(productName, productElement.productName) && Objects.equals(amount, productElement.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, amount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                '}';
    }
}