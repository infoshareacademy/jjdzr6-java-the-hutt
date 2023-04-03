package com.infoshareacademy.service;

import com.infoshareacademy.DTO.FridgeDto;
import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.fridge.Fridge;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.entity.product.ProductUnit;
import com.infoshareacademy.entity.user.User;
import com.infoshareacademy.repository.FridgeRepository;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import com.infoshareacademy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class FridgeServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    FridgeRepository fridgeRepository;

    @Mock
    ProductInFridgeRepository productInFridgeRepository;

    FridgeService fridgeService;

    @Mock
    Pageable pageable;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        fridgeService = new FridgeService(fridgeRepository, userRepository, new ModelMapper(), productInFridgeRepository);
    }

    @Test
    @WithMockUser
    void shouldReturnUserId() {
        //given
        User user = getUser();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        //when
        Long userId = fridgeService.getUserId();

        //then
        assertEquals(user.getId(), userId);

    }


    @Test
    @WithMockUser
    void shouldSaveFridge() {
        //given
        FridgeDto fridgeDto = getDefaultFridgeDto();
        User user = getUser();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        //when
        fridgeService.saveFridge(fridgeDto);

        //then
        verify(fridgeRepository, times(1)).save(any(Fridge.class));
    }


    @Test
    @WithMockUser
    void ShouldReturnFridge() {
        //given
        Fridge fridge = getDefaultFridge();
        User user = getUser();
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        when(fridgeRepository.findById(any())).thenReturn(Optional.of(fridge));

        //when
        FridgeDto fridgeDto = fridgeService.getFridge();

        //then
        assertEquals(2, fridgeDto.getProductsInFridge().size());
    }

    @Test
    @WithMockUser
    void shouldReturnProductsInFridge() {
        //given
        Fridge defaultFridge = getDefaultFridge();
        when(productInFridgeRepository.findProductInFridgeByFridge(any(), any())).thenReturn(new PageImpl<>(defaultFridge.getProductsInFridge()));
        //when
        Page<ProductInFridgeDto> productsInFridge = fridgeService.getProductsInFridge(pageable);

        //then
        assertEquals(defaultFridge.getProductsInFridge().get(0).getProductName(), productsInFridge.stream().toList().get(0).getProductName());
    }

    @Test
    @WithMockUser
    void testIfAddProductsToFridgeForm() {
        //given
        Fridge defaultFridge = getDefaultFridge();
        when(fridgeRepository.findById(any())).thenReturn(Optional.of(defaultFridge));
        //when
        FridgeDto fridgeDto = fridgeService.addProductsToFridgeForm();

        //then
        assertEquals(defaultFridge.getFridgeId(), fridgeDto.getFridgeId());

    }

    @Test
    @WithMockUser
    void shouldMapProductsInFridgeWithNameAsKey() {
        //given
        Fridge defaultFridge = getDefaultFridge();
        when(fridgeRepository.findById(any())).thenReturn(Optional.of(defaultFridge));
        //when
        Map<String, FridgeDto.ProductInFridgeDto> stringProductInFridgeDtoMap = fridgeService.mapProductsInFridgeWithNameAsKey();

        //then
        assertTrue(stringProductInFridgeDtoMap.containsKey("mleko"));

    }

    @Test
    void shouldConvertUnitsInProducts() {
        //given
        ProductInFridge productInFridge = new ProductInFridge();
        productInFridge.setProductName("First");
        productInFridge.setAmount(10000.0);
        productInFridge.setUnit(ProductUnit.MILIGRAM);

        //when
        fridgeService.convertUnitsInProducts(productInFridge);
        //then
        assertEquals(10.0, productInFridge.getAmount());
        assertEquals(ProductUnit.GRAM, productInFridge.getUnit());
    }

    private static User getUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("first");
        return user;
    }

    private static FridgeDto getDefaultFridgeDto() {
        FridgeDto fridgeDto = new FridgeDto();
        fridgeDto.setFridgeId(1L);
        FridgeDto.ProductInFridgeDto first = new FridgeDto.ProductInFridgeDto();
        first.setProductName("mleko");
        first.setAmount(5.0);
        first.setUnit(ProductUnit.LITR);
        FridgeDto.ProductInFridgeDto second = new FridgeDto.ProductInFridgeDto();
        second.setProductName("płatki owsiane");
        second.setAmount(100.0);
        second.setUnit(ProductUnit.GRAM);
        fridgeDto.setProductsInFridge(List.of(first, second));
        return fridgeDto;
    }

    private static Fridge getDefaultFridge() {
        Fridge fridge = new Fridge();
        fridge.setFridgeId(1L);
        ProductInFridge first = new ProductInFridge();
        first.setProductName("mleko");
        first.setAmount(5.0);
        first.setUnit(ProductUnit.LITR);
        ProductInFridge second = new ProductInFridge();
        second.setProductName("płatki owsiane");
        second.setAmount(100.0);
        second.setUnit(ProductUnit.GRAM);
        fridge.setProductsInFridge(List.of(first, second));
        return fridge;
    }
}