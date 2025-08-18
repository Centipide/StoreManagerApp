package com.techlab.inicio.product;

import java.util.Scanner;

@FunctionalInterface
public interface ProductFactory {
    Product create(String name, String brand , int stock, double basePrice, Scanner scanner);
}
