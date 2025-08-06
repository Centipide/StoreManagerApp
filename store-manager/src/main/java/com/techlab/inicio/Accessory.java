package com.techlab.inicio;

public class Accessory extends Product{
    private String name;
    private int stock;
    private double basePrice;

    public Accessory(){}
    public Accessory(String name, int stock, double basePrice){
        this.name = name;
        this.stock = stock;
        this.basePrice = basePrice;
    }
}
