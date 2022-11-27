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
    private String productName;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private ProductUnit unit;

    @Column(name = "expiration_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
