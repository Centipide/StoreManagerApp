package com.techlab.inicio;

public class Drink extends Product {
    private String name;
    private int stock;
    private double basePrice;

    public Drink(){}
    public Drink(String name, int stock, double basePrice){
        this.name = name;
        this.stock = stock;
        this.basePrice = basePrice;
    }
}
