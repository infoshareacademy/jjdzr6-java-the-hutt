package com.infoshareacademy.entity.fridge;


import com.infoshareacademy.entity.product.Product;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fridge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fridgeId;
//
//    private List<Product> productList;
//
//
//    public  Fridge(){
//
//    }
//    public Fridge(List<Product> productList) {
//        this.productList = productList;
//    }
//
//    public Long getFridgeId() {
//        return fridgeId;
//    }
//
//    public void setFridgeId(Long fridgeId) {
//        this.fridgeId = fridgeId;
//    }
//
//    public List<Product> getProductList() {
//        return productList;
//    }
//
//    public void setProductList(List<Product> productList) {
//        this.productList = productList;
//    }
}
