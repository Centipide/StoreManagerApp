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

    void print() {
        System.out.printf("""
                *******************************************
                Nombre de Producto: %s
                Stock disponible: %d
                Precio base: %.2f
                Tipo de producto: %s
                *******************************************
                """, this.name, this.stock, this.basePrice, this.getClass().getSimpleName());
    }
}
