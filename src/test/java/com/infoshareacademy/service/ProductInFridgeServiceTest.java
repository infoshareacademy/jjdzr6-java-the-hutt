package com.infoshareacademy.service;

import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductInFridgeServiceTest {

    ProductInFridgeRepository productInFridgeRepositoryMock = mock(ProductInFridgeRepository.class);
    private final FridgeService fridgeService = mock(FridgeService.class);
    private final ProductInFridgeService productInFridgeService = new ProductInFridgeService(productInFridgeRepositoryMock, fridgeService);

    Fridge setUp(){
        ProductInFridge product = new ProductInFridge();
        product.setProductId(1L);
        product.setProductName("Jajka");
        product.setAmount(1.0);
        product.setUnit(ProductUnit.GRAM);
        product.setExpirationDate(LocalDate.now());

        ProductInFridge productToCompare = new ProductInFridge();
        productToCompare.setProductId(2L);
        productToCompare.setProductName("Jabłka");
        productToCompare.setAmount(2.0);
        productToCompare.setUnit(ProductUnit.LITR);
        productToCompare.setExpirationDate(LocalDate.now());

        Fridge fridge = new Fridge();
        fridge.addProduct(product);
        fridge.addProduct(productToCompare);
        return fridge;
    }

    @Test
    void editProductFromFridge() {
        //given
        Fridge fridge = setUp();
        List<ProductInFridge> listOfProducts = fridge.getProductsInFridge();
        //when
        ProductInFridge productAfterUpdate = productInFridgeService.editProductFromFridge(
                listOfProducts.get(0).getProductId(),
                listOfProducts.get(1));

        //then
        assertNotNull(productAfterUpdate);
        assertEquals("Jabłka", productAfterUpdate.getProductName());
        assertEquals(ProductUnit.LITR, productAfterUpdate.getUnit());
    }

    @Test
    void deleteProductFromFridge(){
        //given
        Fridge fridge = setUp();
        List<ProductInFridge> productsInFridge = fridge.getProductsInFridge();
        //when
        ProductInFridge productFromFridge = productsInFridge.get(0);
        // and then
        assertThrows(NotFoundException.class, () -> productInFridgeService.deleteProductFromFridge(productFromFridge.getProductId()));
    }

    @Test
    void findProductInFridgeById() throws NotFoundException {
        //given
        Fridge fridge = setUp();
        ProductInFridge product = fridge.getProductsInFridge().get(0);
        //when
        when(productInFridgeRepositoryMock.findById(1L)).thenReturn(Optional.ofNullable(product));
        ProductInFridge expectedProduct = productInFridgeService.findProductInFridgeById(product.getProductId());
        //then
        assertEquals(expectedProduct, product);
        assertThrows(NotFoundException.class, ()->productInFridgeService.findProductInFridgeById(3L));
    }
}