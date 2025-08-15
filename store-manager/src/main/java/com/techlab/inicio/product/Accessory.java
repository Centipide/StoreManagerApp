package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

//TODO: Resta aplicar lo mismo a los demás productos. Update podría convertirse en una funcion generica tal vez.

public class Accessory extends Product {
    private final String FIELD_MATERIAL = "material";
    private final String FIELD_COLOUR = "color";

    private static Map<String,String> fullFieldAliasesMap;
    private static ArrayList<String> fullStringKeyFields;
    Map<String, Object> fullFieldValuesMap;

    private String material;
    private String colour;

    public Accessory(String name,String brand ,int stock, double basePrice){
        super(name, brand, stock, basePrice);
        defineFullValuesMap();
        defineFullKeys();
        defineFullAliasesMap();
    }

    private void defineFullKeys(){
        fullStringKeyFields = getBaseKeyFields();
        fullStringKeyFields.add(FIELD_MATERIAL);
        fullStringKeyFields.add(FIELD_COLOUR);
    }

    private void defineFullValuesMap(){
        fullFieldValuesMap = getBaseFieldValuesMap();

        fullFieldValuesMap.put(FIELD_MATERIAL, "indefinido");
        fullFieldValuesMap.put(FIELD_COLOUR, "indefinido");
    }

    private void defineFullAliasesMap(){
        fullFieldAliasesMap = getBaseFieldAliasesMap();
        int index = getNumberOfBaseFields() - 1;

        fullFieldAliasesMap.put(FIELD_MATERIAL, FIELD_MATERIAL);
        fullFieldAliasesMap.put(String.valueOf(index), FIELD_MATERIAL);

        index++;

        fullFieldAliasesMap.put(FIELD_COLOUR, FIELD_COLOUR);
        fullFieldAliasesMap.put(String.valueOf(index), FIELD_COLOUR);
    }

    @Override
    public void setSpecificFields(Scanner scanner) {
        material = ConsoleUtils.scanMaterial(scanner);
        colour = ConsoleUtils.scanColour(scanner);

        fullFieldValuesMap.replace(FIELD_MATERIAL, StringUtils.normalizeKey(material));
        fullFieldValuesMap.replace(FIELD_COLOUR, StringUtils.normalizeKey(colour));
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
                Precio base: %.2f
                Tipo de producto: %s
                Material: %s
                Color: %s
                ********************************************
                """, getId() ,getName(), getBrand(), getStock(), this.getBasePrice(), this.getClass().getSimpleName(),
                getMaterial(), getColour());
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
                break;
            case FIELD_MATERIAL:
                updateMaterial(scanner);
                break;
            case FIELD_COLOUR:
                updateColour(scanner);
                break;
        }
    }

    private void updateMaterial(Scanner scanner) {
        System.out.println("Ingrese un nuevo material: ");
        String newMaterial = ConsoleUtils.scanMaterial(scanner);
        String oldMaterial = getMaterial();

        ConsoleUtils.showUpdate(FIELD_MATERIAL, oldMaterial, newMaterial);

        setMaterial(newMaterial);
        fullFieldValuesMap.replace(FIELD_MATERIAL, StringUtils.normalizeKey(newMaterial));
    }

    private void updateColour(Scanner scanner) {
        System.out.println("Ingrese un nuevo color: ");
        String newColour = ConsoleUtils.scanColour(scanner);
        String oldColour = getColour();

        ConsoleUtils.showUpdate(FIELD_MATERIAL, oldColour, newColour);

        setColour(newColour);
        fullFieldValuesMap.replace(FIELD_MATERIAL, StringUtils.normalizeKey(newColour));
    }

    private void setMaterial(String newMaterial){
        this.material = newMaterial;
    }

    private void setColour(String newColour){
        this.colour = newColour;
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
        return material;
    }
    public String getColour(){
        return colour;
    }
}
