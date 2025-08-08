package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;

import java.io.Console;
import java.util.Map;
import java.util.Scanner;

public class Accessory extends Product {
    private static Map<String,String> fieldsMap;
    private static Map<String,Runnable> updateMap;

    public Accessory(String name, int stock, double basePrice){
        super(name,stock,basePrice);
        createFieldsMap();
        createUpdateMap();
    }

    private void createFieldsMap(){
        fieldsMap = createBaseFieldsMap();
        //todo: por agregar...
    }

    private void createUpdateMap(){
        updateMap = createBaseUpdateMap();
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
    protected void updateField(Scanner scanner,String key) {
        updateMap.get(key);
    }

    @Override
    protected void updateName(Scanner scanner) {
        System.out.println("Ingrese un nuevo nombre: ");
        String newName = ConsoleUtils.scanName(scanner);
    }

    @Override
    protected void updateBasePrice(Scanner scanner) {
        System.out.println("Ingrese un nuevo precio base: ");
    }

    @Override
    protected void updateStock(Scanner scanner) {
        System.out.println("Ingrese una nueva cantidad de stock: ");
    }

    @Override
    protected String getField(String key) {
        return fieldsMap.get(key);
    }

    @Override
    public void printFullFields(){
        printBasicFields();
        //todo: por agregar...
        System.out.println(".");
    }
}
