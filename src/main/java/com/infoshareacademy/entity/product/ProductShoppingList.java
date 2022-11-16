package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.shopping_list.ShoppingList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_shopping_list")
public class ProductShoppingList extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id")
    private ShoppingList shoppingList;


    @Override
    public String toString() {
        return "ProductShoppingList{" +
                "productName='" + productName + '\'' +
                ", amount=" + amount +
                ", unit=" + unit +
                '}';
    }
}
