package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductShoppingList;
import com.infoshareacademy.entity.product.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * A DTO for the {@link ProductShoppingList} entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductShoppingListDto implements Serializable {
    private  Long productId;
    private  String productName;
    private  Double amount;
    private  ProductUnit unit;
    private  ShoppingListDto shoppingList;

    /**
     * A DTO for the {@link com.infoshareacademy.entity.shopping_list.ShoppingList} entity
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShoppingListDto implements Serializable {
        private  Long id;
        private  String name;
        private  Long userId;
    }
}