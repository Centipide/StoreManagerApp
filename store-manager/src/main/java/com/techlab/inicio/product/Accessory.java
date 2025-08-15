package com.techlab.inicio.product;

import com.techlab.inicio.utils.StringUtils;

import java.util.Map;
import java.util.Scanner;

public class Accessory extends Product {
    private static Map<String,String> fieldsMap;

    public Accessory(String name, int stock, double basePrice){
        super(name,stock,basePrice);
        fieldsMap = getFieldsMap();
        //createFieldsMap();
    }
/*
    private void createFieldsMap(){
        fieldsMap = createBaseFieldsMap();
        //todo: por agregar...
    }

 */

    @Override
    public void print() {
        System.out.printf("""
                ********************************************
                ID: %d
                Nombre de Producto: %s
                Stock disponible: %d
                Precio base: %.2f
                Tipo de producto: %s
                ********************************************
                """, getId() ,getName(), getStock(), this.getBasePrice(), this.getClass().getSimpleName());
    }

    @Override
    protected void updateField(ProductManager manager,Scanner scanner,String key) {
        switch (StringUtils.normalizeKey(key)){
            case FIELD_NAME:
                updateName(manager, scanner);
                break;
            case FIELD_BASE_PRICE:
                updateBasePrice(scanner);
                break;
            case FIELD_STOCK:
                updateStock(scanner);
                break;
        }
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

    @Override
    public double getFinalPrice() {
        return 0;
    }
}
