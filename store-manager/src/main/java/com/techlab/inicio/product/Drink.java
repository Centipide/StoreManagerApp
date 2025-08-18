package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Drink extends Product {
    private static final String PRODUCT_TYPE = "Bebida";
    private static final String FIELD_VOLUME = "volumen";
    private static final String FIELD_TYPE = "tipo";
    private static final String FIELD_REC_TEMPERATURE = "temperatura recomendada";

    private static final String[] specificFields = {FIELD_VOLUME, FIELD_TYPE, FIELD_REC_TEMPERATURE};
    private static Map<String,String> fullFieldAliasesMap;
    private static ArrayList<String> fullStringKeyFields;
    Map<String, Object> fullFieldValuesMap;


    private Double volume = null;
    private String type = null;
    private String recommendedTemperature = null;

    public Drink(String name, String brand ,int stock, double basePrice, Scanner scanner){
        super(name, brand, stock, basePrice, scanner);
        fullFieldValuesMap = defineFullValuesMap(specificFields);
        fullStringKeyFields = defineFullKeys(specificFields);
        fullFieldAliasesMap = defineFullAliasesMap(specificFields, fullFieldValuesMap);
    }

    @Override
    public void setSpecificFields() {
        updateVolume();
        updateType();
        updateRecTemperature();

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
                Volumen: %s
                Tipo de bebida: %s
                Temperatura recomendada: %s
                ********************************************
                """, getId() ,getName(), getBrand(), getStock(), getBasePriceString(), PRODUCT_TYPE,
                getVolumeString(), getType(), getRecTemperature());
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
            case FIELD_VOLUME:
                updateVolume();
                break;
            case FIELD_TYPE:
                updateType();
                break;
            case FIELD_REC_TEMPERATURE:
                updateRecTemperature();
        }
    }

    private void updateVolume() {
        updateField(
                FIELD_VOLUME,
                this::getVolume,
                ConsoleUtils::scanVolume,
                this::setVolume,
                fullFieldValuesMap
        );
    }

    private void updateType() {
        updateField(
                FIELD_TYPE,
                this::getType,
                ConsoleUtils::scanType,
                this::setType,
                fullFieldValuesMap
        );
    }

    private void updateRecTemperature() {
        updateField(
                FIELD_REC_TEMPERATURE,
                this::getRecTemperature,
                ConsoleUtils::scanRecTemperature,
                this::setRecTemperature,
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
        return 0;
    }

    private String getRecTemperature() {
        if (recommendedTemperature == null)
            return UNASIGNED;
        return recommendedTemperature;
    }

    public Double getVolume() {
        return volume;
    }
    public String getType() {
        if (type == null)
            return UNASIGNED;
        return type;
    }
    public void setVolume(double volume) {
        this.volume = volume;
    }
    public void setRecTemperature(String recommendedTemperature) {
        this.recommendedTemperature = StringUtils.titleCase(recommendedTemperature);
    }
    public void setType(String type) {
        this.type = StringUtils.titleCase(type);
    }

    public String getVolumeString(){
        if (volume == null)
            return UNASIGNED;
        return String.format("%.2f L", getVolume());
    }
}
