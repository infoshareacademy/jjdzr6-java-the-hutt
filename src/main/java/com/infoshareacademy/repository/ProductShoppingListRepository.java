package com.infoshareacademy.repository;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductShoppingList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductShoppingListRepository extends JpaRepository<ProductShoppingList, Long> {

    @Query("SELECT DISTINCT p FROM ProductShoppingList p WHERE p.shoppingList.id = ?1")
    Page<ProductShoppingList> findProductsInShoppingList(Long id, Pageable pageable);

}
