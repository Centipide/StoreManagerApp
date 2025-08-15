package com.techlab.inicio.product;

import com.techlab.inicio.utils.StringUtils;

import java.util.Map;
import java.util.Scanner;

public class PackagedProduct extends Product {
    private static Map<String,String> fieldsMap;

    public PackagedProduct(String name, String brand ,int stock, double basePrice){
        super(name, brand, stock, basePrice);
        fieldsMap = getBaseFieldAliasesMap();
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
                Marca: %s
                Stock disponible: %d
                Precio base: %.2f
                Tipo de producto: %s
                ********************************************
                """, getId() ,getName(), getBrand(), getStock(), this.getBasePrice(), this.getClass().getSimpleName());
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
            case FIELD_BRAND:
                updateBrand(scanner);
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
    public void setSpecificFields(Scanner scanner) {

    }

    @Override
    public double getFinalPrice() {
        return 0;
    }
}
