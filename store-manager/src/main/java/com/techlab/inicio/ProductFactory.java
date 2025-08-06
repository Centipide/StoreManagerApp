package com.techlab.inicio;
@FunctionalInterface
public interface ProductFactory {
    Product create(String name, int stock, double basePrice);
}
