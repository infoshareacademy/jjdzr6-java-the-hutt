package com.infoshareacademy.repository;

import com.infoshareacademy.entity.shopping_list.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    List<ShoppingList> findByUserId(long id);

    @Transactional
    @Modifying
    void deleteShoppingListById(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM ProductShoppingList WHERE shoppingList.id = :shoppingListId")
    void deleteProductsFromShoppingList(Long shoppingListId);
}
