package com.techlab.inicio.product;

@FunctionalInterface
public interface ProductFactory {
    Product create(String name, String brand ,int stock, double basePrice);
}
