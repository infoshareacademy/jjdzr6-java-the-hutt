//package com.infoshareacademy.service;
//
//import com.infoshareacademy.entity.product.ProductInFridge;
//import com.infoshareacademy.entity.product.ProductUnit;
//import com.infoshareacademy.repository.FridgeRepository;
//import com.infoshareacademy.repository.ProductInFridgeRepository;
//import javassist.NotFoundException;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class FridgeServiceTest {
//
//    ProductInFridgeRepository productInFridgeRepositoryMock = mock(ProductInFridgeRepository.class);
//    FridgeRepository fridgeRepositoryMock = mock(FridgeRepository.class);
//    private final FridgeService fridgeService = new FridgeService(fridgeRepositoryMock, productInFridgeRepositoryMock);
//
//    @Test
//    void editProductFromFridge() {
//        //given
//        ProductInFridge product = new ProductInFridge("Jajka", 1.0, ProductUnit.GRAM, LocalDate.now());
//        ProductInFridge productToCompare = new ProductInFridge("Jabłka", 2.0, ProductUnit.LITR, LocalDate.now());
//        //when
//        ProductInFridge productAfterUpdate = fridgeService.editProductFromFridge(product.getProductId(), productToCompare);
//        //then
//        assertNotNull(productAfterUpdate);
//        assertEquals("Jabłka", productAfterUpdate.getProductName());
//        assertEquals(ProductUnit.LITR, productAfterUpdate.getUnit());
//    }
//
//    @Test
//    void deleteProductFromFridge(){
//        //given
//        ProductInFridge product = new ProductInFridge("Jajka", 1.0, ProductUnit.GRAM, LocalDate.now());
//        //when and then
//        assertThrows(NotFoundException.class, () -> fridgeService.deleteProductFromFridge(product.getProductId()));
//    }
//}