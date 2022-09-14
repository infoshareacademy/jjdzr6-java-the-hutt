package com.infoshareacademy.entity.product;


import com.infoshareacademy.entity.fridge.Fridge;

import javax.persistence.*;

@Entity
@Table(name = "fridge_products")
public class ProductInFridge extends Product{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productInFridgeId;

    @ManyToOne
    @JoinColumn(name = "fridgeId")
    private Fridge fridge;

    public ProductInFridge() {
        super();
    }

    public ProductInFridge(String productName, Double amount){
        super(productName, amount);
    }

    public Long getProductInFridgeId() {
        return productInFridgeId;
    }

    public void setProductInFridgeId(Long productInFridgeId) {
        this.productInFridgeId = productInFridgeId;
    }
}
