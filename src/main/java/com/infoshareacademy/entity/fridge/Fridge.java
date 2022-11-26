package com.infoshareacademy.entity.fridge;

import com.infoshareacademy.entity.product.ProductInFridge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Fridge")
public class Fridge {

    @Id
    @Column(name = "fridge_id")
    private Long fridgeId;

    @OneToMany(mappedBy = "fridge",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Valid
    private List<ProductInFridge> productsInFridge = new ArrayList<>();

    public void addProduct(ProductInFridge product) {
        this.productsInFridge.add(product);
        this.setFridgeId(1L);
        product.setFridge(this);
    }

}
