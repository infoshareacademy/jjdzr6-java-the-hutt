package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.fridge.Fridge;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.Future;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "products_in_fridge")
public class ProductInFridge extends Product {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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
    @Future
    private LocalDate expirationDate;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id")
    private Fridge fridge;

    public ProductInFridge() {
    }

    public ProductInFridge(String productName, Double amount, ProductUnit unit, LocalDate expirationDate) {
        this.productName = productName;
        this.amount = amount;
        this.unit = unit;
        this.expirationDate = expirationDate;
    }

    public Fridge getFridge() {
        return fridge;
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double quantity) {
        this.amount = quantity;
    }

    public ProductUnit getUnit() {
        return unit;
    }

    public void setUnit(ProductUnit unit) {
        this.unit = unit;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

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
