package com.infoshareacademy.product;

public class Product {

    private String name;

    public String getName() {
        return name;
    }

    public Product(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }
}
