package com.infoshareacademy.controller;

import com.infoshareacademy.entity.product.Product;
import com.infoshareacademy.entity.product.ProductInFridge;
import com.infoshareacademy.service.FridgeService;
import com.infoshareacademy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/my_products")
    public String productsAll(Model model){
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/my_products/new")
    public String newProductForm(Model model){
        Product product = new ProductInFridge();
        model.addAttribute("product", product);
        return "new_product";
    }

    @PostMapping("/my_products")
    public String saveProduct(@ModelAttribute("product") Product product){
        productService.addProduct(product);
        return "redirect:/my_products";
    }

    @GetMapping("/my_products/{id}")
    public String deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return "redirect:/my_products";
    }

    @GetMapping("/my_products/edit/{id}")
    public String updateProductForm(@PathVariable Long id, Model model){
        model.addAttribute("product", productService.getProductById(id));
        return "update_product";
    }

    @PostMapping("/my_products/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute("product") Product product, Model model){
        Product existingProduct = productService.getProductById(id);

        existingProduct.setProductId(id);
        existingProduct.setProductName(product.getProductName());
        existingProduct.setAmount(product.getAmount());

        productService.updateProduct(existingProduct);
        return "redirect:/my_products";
    }

}
