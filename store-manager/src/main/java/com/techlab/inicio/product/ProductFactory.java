package com.techlab.inicio.product;

@FunctionalInterface
public interface ProductFactory {
    Product create(String name, int stock, double basePrice);
}
