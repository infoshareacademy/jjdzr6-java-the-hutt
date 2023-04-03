package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ProductInFridgeServiceTest {

    @Mock
    ProductInFridgeRepository productInFridgeRepository;

    @Mock
    FridgeRepository fridgeRepository;
    @Mock
    FridgeService fridgeService;

    ProductInFridgeService productInFridgeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productInFridgeService = new ProductInFridgeService(productInFridgeRepository, fridgeRepository, fridgeService, new ModelMapper());
    }

    @Test
    void findProductInFridgeById() throws NotFoundException {
        //given
        Fridge defaultFridge = getDefaultFridge();
        ProductInFridge product = defaultFridge.getProductsInFridge().get(0);
        when(productInFridgeRepository.findById(1L)).thenReturn(Optional.of(product));
        List<ProductInFridgeDto> productList = getDefaultProductList();

        //when
        ProductInFridgeDto expectedProduct = productInFridgeService.findProductInFridgeById(product.getProductId()).orElseThrow();
        //then
        assertEquals(expectedProduct, productList.get(0));
        assertThrows(NotFoundException.class, () -> productInFridgeService.findProductInFridgeById(3L));
    }
    @Test
    void deleteProductFromFridge() throws NotFoundException {
        //given
        Fridge fridge = getDefaultFridge();
        ProductInFridge product = fridge.getProductsInFridge().get(0);
        when(productInFridgeRepository.findById(1L)).thenReturn(Optional.of(product));
        //when
        productInFridgeService.deleteProductInFridge(1L);

        //then
        verify(productInFridgeRepository, times(1)).deleteById(1L);
        assertThrows(NotFoundException.class, () -> productInFridgeService.deleteProductInFridge(3L));
    }
    @Test
    void editProductFromFridge() throws NotFoundException {
        //given
        Fridge fridge = getDefaultFridge();
        ProductInFridge product = fridge.getProductsInFridge().get(0);
        when(productInFridgeRepository.findById(1L)).thenReturn(Optional.of(product));
        when(fridgeRepository.findById(1L)).thenReturn(Optional.of(fridge));
        FridgeDto.ProductInFridgeDto productInFridgeDto = getDefaultFridgeDto().getProductsInFridge().get(0);

        //when
        productInFridgeService.editProductInFridge(1L, productInFridgeDto);

        //then
        verify(productInFridgeRepository, times(1)).save(product);
        assertThrows(NotFoundException.class, () -> productInFridgeService.editProductInFridge(3L, any()));
    }





    private Fridge getDefaultFridge() {
        ProductInFridge firstProduct = new ProductInFridge();
        firstProduct.setProductId(1L);
        firstProduct.setProductName("Jajka");
        firstProduct.setAmount(1.0);
        firstProduct.setUnit(ProductUnit.GRAM);
        firstProduct.setExpirationDate(LocalDate.now());

        ProductInFridge secondProduct = new ProductInFridge();
        secondProduct.setProductId(2L);
        secondProduct.setProductName("Jabłka");
        secondProduct.setAmount(2.0);
        secondProduct.setUnit(ProductUnit.LITR);
        secondProduct.setExpirationDate(LocalDate.now());

        Fridge fridge = new Fridge();
        fridge.addProduct(firstProduct);
        fridge.addProduct(secondProduct);
        return fridge;
    }
    private List<ProductInFridgeDto> getDefaultProductList() {
        ProductInFridgeDto firstPrduct = new ProductInFridgeDto();
        firstPrduct.setProductId(1L);
        firstPrduct.setProductName("Jajka");
        firstPrduct.setAmount(1.0);
        firstPrduct.setUnit(ProductUnit.GRAM);
        firstPrduct.setExpirationDate(LocalDate.now());

        ProductInFridgeDto secondProduct = new ProductInFridgeDto();
        secondProduct.setProductId(2L);
        secondProduct.setProductName("Jabłka");
        secondProduct.setAmount(2.0);
        secondProduct.setUnit(ProductUnit.LITR);
        secondProduct.setExpirationDate(LocalDate.now());

        return List.of(firstPrduct,secondProduct);
    }
    private FridgeDto getDefaultFridgeDto() {
        FridgeDto.ProductInFridgeDto firstPrduct = new FridgeDto.ProductInFridgeDto();
        firstPrduct.setProductId(1L);
        firstPrduct.setProductName("Jajka");
        firstPrduct.setAmount(1.0);
        firstPrduct.setUnit(ProductUnit.GRAM);
        firstPrduct.setExpirationDate(LocalDate.now());

        FridgeDto.ProductInFridgeDto secondProduct = new FridgeDto.ProductInFridgeDto();
        secondProduct.setProductId(2L);
        secondProduct.setProductName("Jabłka");
        secondProduct.setAmount(2.0);
        secondProduct.setUnit(ProductUnit.LITR);
        secondProduct.setExpirationDate(LocalDate.now());

        FridgeDto fridgeDto = new FridgeDto();
        fridgeDto.setProductsInFridge(List.of(firstPrduct,secondProduct));
        return fridgeDto;
    }

}