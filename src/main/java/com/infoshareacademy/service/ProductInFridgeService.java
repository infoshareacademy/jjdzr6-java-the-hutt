package com.infoshareacademy.service;

import com.infoshareacademy.DTO.ProductInFridgeDto;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.ProductInFridgeRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductInFridgeService {

    private final ProductInFridgeRepository productInFridgeRepository;
    private final FridgeService fridgeService;

    private final ModelMapper modelMapper;

    public ProductInFridgeService(ProductInFridgeRepository productInFridgeRepository, FridgeService fridgeService, ModelMapper modelMapper) {
        this.productInFridgeRepository = productInFridgeRepository;
        this.fridgeService = fridgeService;
        this.modelMapper = modelMapper;
    }

    public ProductInFridgeDto findProductInFridgeById(Long productId) throws NotFoundException {
        return productInFridgeRepository.findById(productId)
                .map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class))
                .orElseThrow(
                        () -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId)));
    }

    public void deleteProductFromFridge(Long productId) throws NotFoundException {
        if (productInFridgeRepository.findById(productId).isPresent()) {
            productInFridgeRepository.deleteById(productId);
        } else throw new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId));
    }

    public void editProductFromFridge(Long productId, ProductInFridgeDto productInFridgeDto) throws NotFoundException {
        Optional<ProductInFridge> productInFridge = Optional.ofNullable(productInFridgeRepository.findById(productId).orElseThrow(() -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId))));

        Optional<ProductInFridgeDto> productDto = productInFridge
                .map(product -> modelMapper.map(product, ProductInFridgeDto.class));

        productDto.get().setFridgeDto(fridgeService.addProductsToFridgeForm().get());
        productDto.get().setProductId(productInFridgeDto.getProductId());
        productDto.get().setProductName(productInFridgeDto.getProductName());
        productDto.get().setAmount(productInFridgeDto.getAmount());
        productDto.get().setUnit(productInFridgeDto.getUnit());
        productDto.get().setExpirationDate(productInFridgeDto.getExpirationDate());

        productInFridgeRepository.save(modelMapper.map(productDto, ProductInFridge.class));

    }
}
