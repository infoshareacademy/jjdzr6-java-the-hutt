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

    public ProductInFridge findProductInFridgeById(Long productId) throws NotFoundException {
        return productInFridgeRepository.findById(productId).orElseThrow(
                () -> new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId)));
    }

    public void deleteProductFromFridge(Long productId) throws NotFoundException {
        if (productInFridgeRepository.findById(productId).isPresent()) {
            productInFridgeRepository.deleteById(productId);
        } else throw new NotFoundException(String.format("Not found Product in Fridge for ID %s", productId));
    }

    public Optional<ProductInFridgeDto> editProductFromFridge(Long productId, ProductInFridgeDto productInFridgeDto) {
        Optional<ProductInFridgeDto> existingProduct = Optional.of(new ProductInFridgeDto());
        if (productInFridgeRepository.findById(productId).isPresent())
            existingProduct = productInFridgeRepository.findById(productId)
                    .map(productInFridge -> modelMapper.map(productInFridge, ProductInFridgeDto.class));

        existingProduct.get().setFridgeDto(fridgeService.addProductsToFridgeForm());
        existingProduct.get().setProductId(productInFridgeDto.getProductId());
        existingProduct.get().setProductName(productInFridgeDto.getProductName());
        existingProduct.get().setAmount(productInFridgeDto.getAmount());
        existingProduct.get().setUnit(productInFridgeDto.getUnit());
        existingProduct.get().setExpirationDate(productInFridgeDto.getExpirationDate());
        modelMapper.map(productInFridgeRepository.save(existingProduct), ProductInFridgeDto.class);
        return existingProduct;
    }



}
