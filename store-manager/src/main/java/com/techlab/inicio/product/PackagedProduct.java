package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class PackagedProduct extends Product {
    private static final String PRODUCT_TYPE = "Empaquetado";

    private static final String FIELD_WEIGHT = "peso";
    private static final String FIELD_EXP_DATE = "fecha de vencimiento";
    private static final String FIELD_INGREDIENTS = "ingredientes";

    private static final String[] specificFields = {FIELD_WEIGHT, FIELD_EXP_DATE, FIELD_INGREDIENTS};
    private static Map<String,String> fullFieldAliasesMap;
    private static ArrayList<String> fullStringKeyFields;
    Map<String, Object> fullFieldValuesMap;

    private Double weight = null;
    private LocalDate expirationDate = null;
    private String ingredients = null;

    public PackagedProduct(String name, String brand ,int stock, double basePrice, Scanner scanner){
        super(name, brand, stock, basePrice, scanner);
        fullFieldValuesMap = defineFullValuesMap(specificFields);
        fullStringKeyFields = defineFullKeys(specificFields);
        fullFieldAliasesMap = defineFullAliasesMap(specificFields, fullFieldValuesMap);
    }

    @Override
    public void setSpecificFields() {
        updateWeight();
        updateExpirationDate();
        updateIngredients();

        System.out.println("Carga de datos especificos exitosa.");
    }

    @Override
    public void print() {
        System.out.printf("""
                ********************************************
                ID: %d
                Nombre de Producto: %s
                Marca: %s
                Stock disponible: %d
                Precio base: %s
                Tipo de producto: %s
                Peso: %s
                Fecha de vencimiento: %s
                Ingredientes: %s
                ********************************************
                """, getId() ,getName(), getBrand(), getStock(), getBasePriceString(), PRODUCT_TYPE,
                getWeightString(), getExpirationDateString(), getIngredients());
    }



    @Override
    protected void updateField(ProductManager manager, String key) {
        switch (StringUtils.normalizeKey(key)){
            case FIELD_NAME:
                updateName(manager);
                break;
            case FIELD_BASE_PRICE:
                updateBasePrice();
                break;
            case FIELD_STOCK:
                updateStock();
                break;
            case FIELD_BRAND:
                updateBrand();
                break;
            case FIELD_WEIGHT:
                updateWeight();
                break;
            case FIELD_EXP_DATE:
                updateExpirationDate();
                break;
            case FIELD_INGREDIENTS:
                updateIngredients();
        }
    }

    private void updateWeight() {
        updateField(
                FIELD_WEIGHT,
                this::getWeight,
                ConsoleUtils::scanWeight,
                this::setWeight,
                fullFieldValuesMap
        );
    }

    private void updateExpirationDate() {
        updateField(
                FIELD_EXP_DATE,
                this::getExpirationDate,
                ConsoleUtils::scanExpirationDate,
                this::setExpirationDate,
                fullFieldValuesMap
        );
    }

    private void updateIngredients() {
        updateField(
                FIELD_INGREDIENTS,
                this::getIngredients,
                ConsoleUtils::scanIngredients,
                this::setIngredients,
                fullFieldValuesMap
        );
    }

    @Override
    protected String getField(String key) {
        return fullFieldAliasesMap.get(key);
    }

    @Override
    public void printFullFields(){
        for (int i = 0; i < fullFieldValuesMap.size(); i++) {
            System.out.printf("%d. %s: %s\n", i + 1, StringUtils.titleCase(fullStringKeyFields.get(i)) ,
                    fullFieldValuesMap.get(fullStringKeyFields.get(i)));
        }
    }

    @Override
    public double getFinalPrice() {
        return getBasePrice() * (1 + TaxRates.VAT_REDUCED);
    }

    public void setWeight(double weight){
        this.weight = weight;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = StringUtils.titleCase(ingredients);
    }

    public Double getWeight() {
        return weight;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }
    public String getIngredients() {
        if (ingredients == null)
            return UNSIGNED;
        return ingredients;
    }

    public String getWeightString(){
        if (weight == null)
            return UNSIGNED;

        if (weight < 1000)
            return String.format("%.2f g", weight);
        else
            return String.format("%.2f kg", weight/1000);
    }
    public String getExpirationDateString() {
        if (expirationDate == null)
            return UNSIGNED;

        return expirationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
