package com.techlab.inicio;

abstract class Product {
    private final int id;                //final: Una vez asignado no se puede cambiar
    private static int counterId = 0;   //static: Pertenece a la clase misma, no a las instancias

    private String name ;
    private String category;
    private int stock;
    private double basePrice;
    private double taxRate;

    {
        counterId++;
        id = counterId;
    }

    protected Product(){}
    protected Product(String name, String category, int stock, double basePrice){
        setName(name);
        setStock(stock);
        setBasePrice(basePrice);
        setCategory(category);
    }

    protected void setName(String name) { //todo: pasar a titleCase "Cafe Chái"
        this.name = name;
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
    protected void setCategory(String category){
        this.category = category;
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
    public String getCategory() {
        return category;
    }
}
