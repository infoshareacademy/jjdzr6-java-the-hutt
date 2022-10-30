package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInFridgeDto implements Serializable {
    private Long productId;
    private String productName;
    private Double amount;
    private ProductUnit unit;
    @Future
    private LocalDate expirationDate;
    private FridgeDto fridgeDto;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FridgeDto implements Serializable {
        private Long fridgeId;
    }
}