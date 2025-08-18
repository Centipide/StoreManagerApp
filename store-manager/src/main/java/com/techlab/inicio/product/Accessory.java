package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

//TODO: Resta aplicar lo mismo a los demás productos. Update podría convertirse en una funcion generica tal vez.

public class Accessory extends Product {
    private static final String PRODUCT_TYPE = "Accesorio";
    private static final String FIELD_MATERIAL = "material";
    private static final String FIELD_COLOUR = "color";

    private static final String[] specificFields = {FIELD_MATERIAL, FIELD_COLOUR};
    private static Map<String,String> fullFieldAliasesMap;
    private static ArrayList<String> fullStringKeyFields;
    Map<String, Object> fullFieldValuesMap;

    private String material = null;
    private String colour = null;

    public Accessory(String name,String brand ,int stock, double basePrice, Scanner scanner){
        super(name, brand, stock, basePrice, scanner);
        fullFieldValuesMap = defineFullValuesMap(specificFields);
        fullStringKeyFields = defineFullKeys(specificFields);
        fullFieldAliasesMap = defineFullAliasesMap(specificFields, fullFieldValuesMap);
    }

    @Override
    public void setSpecificFields() {
        updateMaterial();
        updateColour();

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
                Material: %s
                Color: %s
                ********************************************
                """, getId() ,getName(), getBrand(), getStock(), getBasePriceString(), PRODUCT_TYPE,
                getMaterial(), getColour());
    }

    @Override
    protected void updateField(ProductManager manager,String key) {
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
            case FIELD_MATERIAL:
                updateMaterial();
                break;
            case FIELD_COLOUR:
                updateColour();
        }
    }

    private void updateMaterial() {
        updateField(
                FIELD_MATERIAL,
                this::getMaterial,
                ConsoleUtils::scanMaterial,
                this::setMaterial,
                fullFieldValuesMap
        );
    }

    private void updateColour() {
        updateField(
                FIELD_COLOUR,
                this::getColour,
                ConsoleUtils::scanColour,
                this::setColour,
                fullFieldValuesMap
        );
    }

    private void setMaterial(String newMaterial){
        this.material = StringUtils.titleCase(newMaterial);
    }

    private void setColour(String newColour){
        this.colour = StringUtils.titleCase(newColour);
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
        return 0;
    }
    public String getMaterial() {
        if (material == null || material.isEmpty())
            return UNASIGNED;
        return material;
    }
    public String getColour(){
        if (colour == null || colour.isEmpty())
            return UNASIGNED;
        return colour;
    }
}
