package com.techlab.inicio.product;

import com.techlab.inicio.utils.StringUtils;

abstract public class Product {
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

    protected Product(){}
    protected Product(String name, int stock, double basePrice){
        setName(name);
        setStock(stock);
        setBasePrice(basePrice);
    }

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

    abstract public void print();
}
