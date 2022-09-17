package com.infoshareacademy.service;

import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductInFridge> getAllProducts(){
        return productRepository.findAll();
    }

    public ProductInFridge addProduct(ProductInFridge product){
        return productRepository.save(product);
    }

    public ProductInFridge updateProduct(ProductInFridge product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}