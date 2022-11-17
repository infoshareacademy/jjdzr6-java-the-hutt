package com.infoshareacademy.entity.product;

import com.infoshareacademy.entity.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products_recipe")
public class ProductRecipe extends Product {

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

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;


    @Override
    public String toString() {

        return  productName + '\'' +
                ", ilość:" + amount + " " + unit.getValue();
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
