package com.infoshareacademy.entity.fridge;


import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "fridge")
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", unique = true)
    private String productInFridgeName;

    @Column(name = "quantity")
    private Double quantity;

    public Fridge() {
    }

    public Fridge(String productInFridgeName, Double quantity) {
        this.productInFridgeName = productInFridgeName;
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getProductInFridgeName() {
        return productInFridgeName;
    }

    public void setProductInFridgeName(String productInFridgeName) {
        this.productInFridgeName = productInFridgeName;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }
}
