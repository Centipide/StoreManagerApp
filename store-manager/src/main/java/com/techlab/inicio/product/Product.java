package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

abstract public class Product {
    public static final String FIELD_NAME = "nombre";
    public static final String FIELD_BASE_PRICE = "precio base";
    public static final String FIELD_STOCK = "stock";

    // explicacion en draw.io
    public static final ArrayList<String> BASE_FIELDS = new ArrayList<>();
    public static final Map<String, String> BASE_FIELDS_MAP = new HashMap<>();
    static {
        BASE_FIELDS.add(FIELD_NAME);
        BASE_FIELDS.add(FIELD_BASE_PRICE);
        BASE_FIELDS.add(FIELD_STOCK);
        for (int i = 0; i < BASE_FIELDS.size(); i++) {
            BASE_FIELDS_MAP.put(BASE_FIELDS.get(i), BASE_FIELDS.get(i));
            BASE_FIELDS_MAP.put(String.valueOf(i + 1), BASE_FIELDS.get(i));
        }
    }

    private static int counterId = 0;

    private final int id;
    public final ArrayList<String> stringKeyFields;
    private final Map<String, String> fieldsMap;
    private String name ;
    private int stock;
    private double basePrice;
    private double taxRate;

    protected Product(String name, int stock, double basePrice){
        counterId++;
        id = counterId;

        setName(name);
        setStock(stock);
        setBasePrice(basePrice);

        /*
        Las clases hijas puedan usar estos 3 campos básicos y el mapeo,
        para que cada una le vaya agregando campos a su preferencia. De esta forma
        evitamos repetir código y nos aseguramos que todas tengan estos 3 campos.
         */
        stringKeyFields = new ArrayList<>(BASE_FIELDS);
        fieldsMap = new HashMap<>(BASE_FIELDS_MAP);
    }

    abstract public double getFinalPrice();
    abstract public void print();
    abstract protected String getField(String key);
    abstract protected void updateField(ProductManager manager, Scanner scanner,String key);

    protected void updateName(ProductManager manager, Scanner scanner) {
        System.out.println("Ingrese un nuevo nombre: ");
        String newName = ConsoleUtils.scanName(scanner);
        String oldName = getName();

        ConsoleUtils.showUpdate(FIELD_NAME, oldName, newName);
        setName(newName);
        manager.updateProductsNameMap(StringUtils.normalizeKey(oldName), StringUtils.normalizeKey(newName));
    }

    protected void updateBasePrice(Scanner scanner) {
        System.out.println("Ingrese un nuevo precio base: ");
        double newPrice = ConsoleUtils.scanBasePrice(scanner);

        ConsoleUtils.showUpdate(FIELD_BASE_PRICE, String.valueOf(getBasePrice()), String.valueOf(newPrice));
        setBasePrice(newPrice);
    }

    protected void updateStock(Scanner scanner) {
        System.out.println("Ingrese una nueva cantidad de stock: ");
        int newStock = ConsoleUtils.scanStock(scanner);

        ConsoleUtils.showUpdate(FIELD_STOCK, String.valueOf(getStock()), String.valueOf(newStock));
        setStock(newStock);
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
        System.out.println("1. Nombre: " + name);
        System.out.println("2. Precio: $" + basePrice);
        System.out.println("3. Stock:  " + id);
    }

    public Map<String, String> getFieldsMap() {
        return fieldsMap;
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
