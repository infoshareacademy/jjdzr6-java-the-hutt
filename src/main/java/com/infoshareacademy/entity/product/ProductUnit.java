package com.infoshareacademy.entity.product;

public enum ProductUnit {
    MILIGRAM("mg"),
    GRAM("g"),
    KILOGRAM("kg"),
    MILILITR("ml"),
    LITR("l"),
    PIECE("szt."),
    PACK("op."),
    ;

    private final String value;

    ProductUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
