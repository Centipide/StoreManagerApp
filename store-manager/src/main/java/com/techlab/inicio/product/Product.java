package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract public class Product {
    public static final String FIELD_NAME = "nombre";
    public static final String FIELD_BRAND = "marca";
    public static final String FIELD_BASE_PRICE = "precio base";
    public static final String FIELD_STOCK = "stock";

    public static final ArrayList<String> BASE_FIELDS = new ArrayList<>();
    public static final Map<String, String> BASE_FIELD_ALIASES_MAP = new HashMap<>();
    Map<String,Object> baseFieldValuesMap;
    static {
        BASE_FIELDS.add(FIELD_NAME);
        BASE_FIELDS.add(FIELD_BRAND);
        BASE_FIELDS.add(FIELD_BASE_PRICE);
        BASE_FIELDS.add(FIELD_STOCK);
        for (int i = 0; i < BASE_FIELDS.size(); i++) {
            BASE_FIELD_ALIASES_MAP.put(BASE_FIELDS.get(i), BASE_FIELDS.get(i));
            BASE_FIELD_ALIASES_MAP.put(String.valueOf(i + 1), BASE_FIELDS.get(i));
        }
    }

    private static int counterId = 0;

    private final int id;
    public final ArrayList<String> baseStringKeyFields;
    private final Map<String, String> baseFieldAliasesMap;
    private String name ;
    private int stock;
    private double basePrice;
    private String brand;
    private double taxRate;

    abstract public void setSpecificFields(Scanner scanner);
    abstract public double getFinalPrice();
    abstract public void print();
    abstract protected String getField(String key);
    abstract protected void updateField(ProductManager manager, Scanner scanner,String key);

    protected Product(String name, String brand, int stock, double basePrice){
        counterId++;
        id = counterId;

        setName(name);
        setStock(stock);
        setBasePrice(basePrice);
        setBrand(brand);

        /*
        Las clases hijas puedan usar estos 3 campos básicos y el mapeo,
        para que cada una le vaya agregando campos a su preferencia. De esta forma
        evitamos repetir código y nos aseguramos que todas tengan estos 3 campos.
         */
        baseStringKeyFields = new ArrayList<>(BASE_FIELDS);
        baseFieldAliasesMap = new HashMap<>(BASE_FIELD_ALIASES_MAP);

        createBaseFieldValuesMap();
    }

    private void createBaseFieldValuesMap(){
        baseFieldValuesMap = new HashMap<>();
        baseFieldValuesMap.put(FIELD_NAME, getName());
        baseFieldValuesMap.put(FIELD_BRAND, getBrand());
        baseFieldValuesMap.put(FIELD_BASE_PRICE, getBasePrice());
        baseFieldValuesMap.put(FIELD_STOCK, getStock());
    }

    protected void updateName(ProductManager manager, Scanner scanner) {
        System.out.println("Ingrese un nuevo nombre: ");
        String newName = ConsoleUtils.scanName(scanner);
        String oldName = getName();

        ConsoleUtils.showUpdate(FIELD_NAME, oldName, newName);

        setName(newName);
        baseFieldValuesMap.replace(FIELD_NAME, StringUtils.normalizeKey(newName));

        manager.updateProductsNameMap(StringUtils.normalizeKey(oldName), StringUtils.normalizeKey(newName));
    }

    protected void updateBrand (Scanner scanner){
        System.out.println("Ingrese un nuevo nombre de marca: ");
        String newName = ConsoleUtils.scanBrand(scanner);
        String oldName = getBrand();

        ConsoleUtils.showUpdate(FIELD_BRAND, oldName, newName);

        setBrand(newName);
        baseFieldValuesMap.replace(FIELD_BRAND, StringUtils.normalizeKey(newName));
    }

    protected void updateBasePrice(Scanner scanner) {
        System.out.println("Ingrese un nuevo precio base: ");
        double newPrice = ConsoleUtils.scanBasePrice(scanner);

        ConsoleUtils.showUpdate(FIELD_BASE_PRICE, String.valueOf(getBasePrice()), String.valueOf(newPrice));

        setBasePrice(newPrice);
        baseFieldValuesMap.replace(FIELD_BASE_PRICE, newPrice);
    }

    protected void updateStock(Scanner scanner) {
        System.out.println("Ingrese una nueva cantidad de stock: ");
        int newStock = ConsoleUtils.scanStock(scanner);

        ConsoleUtils.showUpdate(FIELD_STOCK, String.valueOf(getStock()), String.valueOf(newStock));

        setStock(newStock);
        baseFieldValuesMap.replace(FIELD_STOCK, newStock);
    }

    public void decreaseStock(int amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad a disminuir debe ser positiva");
        }
        if (amount > stock) {
            throw new IllegalStateException("No hay suficiente stock disponible");
        }
        stock -= amount;
    }


    protected void setName(String name) {
        this.name = StringUtils.titleCase(name);
    }
    protected void setBrand(String brand){
        this.brand = StringUtils.titleCase(brand);
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

    public void printBasicFields() {
        for (int i = 0; i < baseStringKeyFields.size(); i++) {
            System.out.printf("%d. %s: %s\n", i + 1, baseStringKeyFields.get(i), baseFieldValuesMap.get(baseStringKeyFields.get(i)));
        }

        /*
        System.out.println("1. Nombre: " + name);
        System.out.println("2. Marca: " + brand);
        System.out.println("3. Precio: $" + basePrice);
        System.out.println("4. Stock:  " + id);
         */
    }


    public ArrayList<String> getBaseKeyFields(){
        return baseStringKeyFields;
    }
    public int getNumberOfBaseFields(){
        return baseStringKeyFields.size();
    }
    public Map<String, String> getBaseFieldAliasesMap() {
        return baseFieldAliasesMap;
    }
    public Map<String, Object> getBaseFieldValuesMap(){
        return baseFieldValuesMap;
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
    String getBrand() {
        return brand;
    }

}
