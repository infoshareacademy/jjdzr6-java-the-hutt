package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.fridge.Fridge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_in_fridge")
public class ProductInFridge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "product_name")
    @NotBlank(message = "Nazwa produktu nie może być pusta!")
    private String productName;

    @Column(name = "amount")
    @NotNull(message = "Ilość produktu nie może być pusta!")
    @Min(value = 1, message = "Ilość produktu musi być większa lub równa 1!")
    @Max(value = 999, message = "Ilość produktu nie może być mniejsza niż 1000!")
    private Double amount;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @Column(name = "expiration_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Data spożycia musi być datą przyszłą!")
    private LocalDate expirationDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    @Override
    public String toString() {
        return "ProductInFridge{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", unit=" + unit +
                ", expirationDate=" + expirationDate +
                ", fridge=" + fridge +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductInFridge that = (ProductInFridge) o;
        return Objects.equals(productId, that.productId) && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName);
    }
}
