package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FridgeDto implements Serializable {
    private Long fridgeId;
    private List<ProductInFridgeDto> productsInFridge;

    public void addProductDto(FridgeDto.ProductInFridgeDto product) {
        if(productsInFridge == null){
            this.productsInFridge = new ArrayList<>();
        }
        this.productsInFridge.add(product);
        this.setFridgeId(1L);
        product.setFridgeDto(this);
    }

    public List<ProductInFridgeDto> getProductsInFridge() {
        return productsInFridge;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInFridgeDto implements Serializable {
        private Long productId;
        private String productName;
        private Double amount;
        private ProductUnit unit;
        @Future
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate expirationDate;
        private FridgeDto fridgeDto;
    }
}