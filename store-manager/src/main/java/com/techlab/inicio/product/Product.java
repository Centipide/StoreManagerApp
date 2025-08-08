package com.techlab.inicio.product;

import com.techlab.inicio.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract public class Product {
    public static final String FIELD_NAME = "nombre";
    public static final String FIELD_BASE_PRICE = "precio base";
    public static final String FIELD_STOCK = "stock";

    private final int id;                //final: Una vez asignado no se puede cambiar
    private static int counterId = 0;   //static: Pertenece a la clase misma, no a las instancias
    private String name ;
    private int stock;
    private double basePrice;
    private double taxRate;

    {
        counterId++;
        id = counterId;
    }

    protected Product(String name, int stock, double basePrice){
        setName(name);
        setStock(stock);
        setBasePrice(basePrice);
    }

    abstract public void print();
    abstract protected void updateField(String key, Scanner scanner);
    abstract protected void updateName();
    abstract protected void updateBasePrice();
    abstract protected void updateStock();
    abstract protected String getField(String key);

    protected void setName(String name) {
        this.name = StringUtils.titleCase(name);
    }
    protected void setStock(int stock) {
        if (stock < 0){
            System.out.println("Error al establecer el stock");
            return;
        }
        this.stock = stock;
    }
    protected void setBasePrice(double basePrice) {
        if (basePrice < 0){
            System.out.println("Error al establecer el precio");
            return;
        }
        this.basePrice = basePrice;
    }
    protected void setTaxRate(double taxRate){ //todo: dudo si ponerlo como private o abstract
        this.taxRate = taxRate;
    }

    protected Map<String, String> createBaseFieldsMap(){
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("1", FIELD_NAME);
        fieldsMap.put(FIELD_NAME, FIELD_NAME);

        fieldsMap.put("2", FIELD_BASE_PRICE);
        fieldsMap.put(FIELD_BASE_PRICE, FIELD_BASE_PRICE);

        fieldsMap.put("3", FIELD_STOCK);
        fieldsMap.put(FIELD_STOCK, FIELD_STOCK);

        return fieldsMap;
    }

    protected Map<String, Runnable> createBaseUpdateMap(){
        Map<String, Runnable> updateMap = new HashMap<>();
        updateMap.put(FIELD_NAME, this::updateName);
        updateMap.put(FIELD_BASE_PRICE, this::updateBasePrice);
        updateMap.put(FIELD_STOCK, this::updateStock);

        return updateMap;
    }

    public void printBasicFields() {
        System.out.println("1. Nombre: " + name);
        System.out.println("2. Precio: $" + basePrice);
        System.out.println("3. Stock:  " + id);
    }

    public void printFullFields() {
        printBasicFields();
    }

    public String getName() {
        return name;
    }
    public int getStock() {
        return stock;
    }
    public double getTaxRate() {
        return taxRate;
    }
    public double getBasePrice() {
        return basePrice;
    }
    public int getId() {
        return id;
    }


}
