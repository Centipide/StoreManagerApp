package com.techlab.inicio;

public class PackagedProduct extends Product{
    private String name;
    private int stock;
    private double basePrice;

    public PackagedProduct(){}
    public PackagedProduct(String name, int stock, double basePrice){
        this.name = name;
        this.stock = stock;
        this.basePrice = basePrice;
    }
}
