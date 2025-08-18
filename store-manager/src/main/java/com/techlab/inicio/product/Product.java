package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

abstract public class Product {
    protected static final String UNASIGNED = "No asignado";

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

    protected final Scanner scanner;
    private final int id;
    public final ArrayList<String> baseStringKeyFields;
    private final Map<String, String> baseFieldAliasesMap;
    private String name ;
    private int stock;
    private double basePrice;
    private String brand;
    private double taxRate;

    abstract public void setSpecificFields();
    abstract public double getFinalPrice();
    abstract public void print();
    abstract protected String getField(String key);
    abstract protected void updateField(ProductManager manager, String key);

    protected Product(String name, String brand, int stock, double basePrice, Scanner scanner){
        this.scanner = scanner;
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

    protected <T> void updateField(
            String fieldName,
            Supplier<T> getter,
            Function<Scanner, T> reader,
            Consumer<T> setter,
            Map<String, Object> mapToUpdate
    ) {
        T newValue = reader.apply(scanner);
        T oldValue = getter.get();

        if (oldValue != null && oldValue != UNASIGNED) {
            ConsoleUtils.showUpdate(fieldName, oldValue.toString(), newValue.toString());
        }

        setter.accept(newValue);
        mapToUpdate.replace(fieldName, StringUtils.normalizeKey(newValue.toString()));
    }

    protected void updateName(ProductManager manager) {
        String oldName = getName();

        updateField(
                FIELD_NAME,
                this::getName,
                ConsoleUtils::scanName,
                this::setName,
                baseFieldValuesMap
        );

        manager.updateProductsNameMap(StringUtils.normalizeKey(oldName), StringUtils.normalizeKey(getName()));
    }

    protected void updateBrand (){
        updateField(
                FIELD_BRAND,
                this::getBrand,
                ConsoleUtils::scanBrand,
                this::setBrand,
                baseFieldValuesMap);
    }

    protected void updateBasePrice() {
        updateField(
                FIELD_BASE_PRICE,
                this::getBasePrice,
                ConsoleUtils::scanBasePrice,
                this::setBasePrice,
                baseFieldValuesMap);
    }

    protected void updateStock() {
        updateField(
                FIELD_STOCK,
                this::getStock,
                ConsoleUtils::scanStock,
                this::setStock,
                baseFieldValuesMap
        );
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



    protected Map<String, Object> defineFullValuesMap(String[] specificFields){
        Map<String, Object> fullFieldValuesMap = getBaseFieldValuesMap();

        for (String field : specificFields) {
            fullFieldValuesMap.put(field, "No asignado");
        }

        return fullFieldValuesMap;
    }

    protected ArrayList<String> defineFullKeys(String[] specificFields){
        ArrayList<String> fullStringKeyFields = getBaseKeyFields();
        fullStringKeyFields.addAll(Arrays.asList(specificFields));

        return fullStringKeyFields;
    }

    protected Map<String,String> defineFullAliasesMap(String[] specificFields, Map<String, Object> fullFieldValuesMap){
        Map<String,String> fullFieldAliasesMap = getBaseFieldAliasesMap();
        int index = getNumberOfBaseFields() - 1;

        for (String field : specificFields) {
            fullFieldAliasesMap.put(field, field);
            fullFieldAliasesMap.put(String.valueOf(index++), field);
        }

        return fullFieldAliasesMap;
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

    public String getBasePriceString(){
        return String.format("$%.2f", getBasePrice());
    }


}
