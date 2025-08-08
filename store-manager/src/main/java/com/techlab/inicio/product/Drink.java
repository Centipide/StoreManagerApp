package com.techlab.inicio.product;

import java.util.HashMap;
import java.util.Map;

public class Drink extends Product {
    private static Map<String,String> fieldsMap;


    public Drink(String name, int stock, double basePrice){
        super(name,stock,basePrice);
        createFieldsMap();
    }

    private void createFieldsMap(){
        fieldsMap = createBaseFieldsMap();
        //todo: por agregar...
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

    @Override
    public void printFullFields(){
        printBasicFields();
        System.out.println(".");
    }
}
