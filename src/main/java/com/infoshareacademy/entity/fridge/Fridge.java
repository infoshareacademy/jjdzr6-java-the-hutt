package com.infoshareacademy.entity.fridge;

import com.infoshareacademy.entity.product.ProductInFridge;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Fridge")
public class Fridge {

    @Id
    @Column(name = "fridge_id")
    private Long fridgeId = 1L;

    @OneToMany(mappedBy = "fridge",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductInFridge> productsInFridge = new ArrayList<>();

    public void addProduct(ProductInFridge product) {
        this.productsInFridge.add(product);
        this.setFridgeId(1L);
        product.setFridge(this);
    }

    public Long getFridgeId() {
        return fridgeId;
    }

    public void setFridgeId(Long fridgeId) {
        this.fridgeId = fridgeId;
    }

    public List<ProductInFridge> getProductsInFridge() {
        return productsInFridge;
    }

    public void setProductsInFridge(List<ProductInFridge> productsInFridge) {
        this.productsInFridge = productsInFridge;
    }

    public Fridge() {
    }


}
