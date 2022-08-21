package com.infoshareacademy.entity.product;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_elements")
public class ProductElement {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long elementId;

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

    public void setElementId(Long id) {
        this.elementId = id;
    }

    public Long getElementId() {
        return elementId;
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
        return Objects.equals(elementId, productElement.elementId) && Objects.equals(productName, productElement.productName) && Objects.equals(amount, productElement.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementId, productName, amount);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + elementId +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                '}';
    }
}