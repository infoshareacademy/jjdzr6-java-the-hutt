package com.infoshareacademy.DTO;

import com.infoshareacademy.entity.product.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInFridgeDto implements Serializable {
    private Long productId;
    @NotBlank(message = "Nazwa produktu nie może być pusta!")
    private String productName;
    @NotNull(message = "Ilość produktu nie może być pusta!")
    @Min(value = 1, message = "Ilość produktu musi być większa lub równa 1!")
    @Max(value = 999, message = "Ilość produktu nie może być mniejsza niż 1000!")
    private Double amount;
    private ProductUnit unit;
    @Future(message = "Data spożycia musi być datą przyszłą!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private FridgeDto fridgeDto;



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FridgeDto implements Serializable {
        private Long fridgeId;
    }
}