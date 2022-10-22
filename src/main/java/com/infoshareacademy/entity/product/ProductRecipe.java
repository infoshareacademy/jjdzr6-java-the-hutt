package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.recipe.Recipe;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "products_recipe")
public class ProductRecipe extends Product {

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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public ProductRecipe() {
    }

    public ProductRecipe(String productName, Double amount) {
        this.productName = productName;
        this.amount = amount;
    }

    public ProductRecipe(String productName, Double amount, ProductUnit unit) {
        this.productName = productName;
        this.amount = amount;
        this.unit = unit;
    }

    public void setProductId(Long id) {
        this.productId = id;
    }

    public Long getProductId() {
        return productId;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
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

    @Override
    public String toString() {
        return "ProductRecipe{" +
                "productName='" + productName + '\'' +
                ", amount=" + amount +
                ", unit=" + unit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRecipe that = (ProductRecipe) o;
        return Objects.equals(productId, that.productId) && Objects.equals(productName, that.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName);
    }
}
