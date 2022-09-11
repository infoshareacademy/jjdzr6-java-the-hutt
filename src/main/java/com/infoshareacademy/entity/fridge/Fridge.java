package com.infoshareacademy.entity.fridge;


import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.product.ProductInFridge;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fridge")
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fridgeId;


    @OneToMany(mappedBy = "fridge", cascade = CascadeType.ALL)
    private List<ProductInFridge> productInFridgeList;


    public  Fridge(){

    }
    public Fridge(List<ProductInFridge> productList) {
        this.productInFridgeList = productList;
    }

    public Long getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(Long fridgeId) {
        this.fridgeId = fridgeId;
    }

    public List<ProductInFridge> getProductList() {
        return productInFridgeList;
    }

    public void setProductList(List<ProductInFridge> productList) {
        this.productInFridgeList = productList;
    }
}
