package com.techlab.inicio.order;

import com.techlab.inicio.product.Product;

public class OrderLine {
    private final Product product;
    private final int units;

    public OrderLine(Product product, int units){
        this.product = product;
        this.units = units;
    }

    public void print() {
        System.out.printf("""
                ID: %d
                Nombre: %s
                Unidades pedidas: %d
                Precio final: %.2f
                """, product.getId(), product.getName(), units, calcTotalPrice());
    }

    private double calcTotalPrice(){
        double unitPrice = product.getFinalPrice();
        return unitPrice * units;
    }
}
