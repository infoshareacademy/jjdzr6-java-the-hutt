package com.infoshareacademy.entity.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_elements")
public class ProductElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elementId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

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