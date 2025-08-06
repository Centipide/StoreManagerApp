package com.techlab.inicio;

public class Electronic extends Product {
    private String name;
    private int stock;
    private double basePrice;

    public Electronic(){}
    public Electronic(String name, int stock, double basePrice){
        this.name = name;
        this.stock = stock;
        this.basePrice = basePrice;
    }
}
