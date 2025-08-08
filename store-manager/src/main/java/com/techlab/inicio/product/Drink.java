package com.techlab.inicio.product;

public class Drink extends Product {

    public Drink(String name, int stock, double basePrice){
        super(name,stock,basePrice);
    }

    @Override
    public void print() {
        System.out.printf("""
                *******************************************
                ID: %d
                Nombre de Producto: %s
                Stock disponible: %d
                Precio base: %.2f
                Tipo de producto: %s
                *******************************************
                """, getId() ,getName(), getStock(), this.getBasePrice(), this.getClass().getSimpleName());
    }
}
