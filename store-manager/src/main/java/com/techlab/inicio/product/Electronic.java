package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Electronic extends Product {
    private static final String PRODUCT_TYPE = "Electronico";
    private static final String FIELD_MODEL = "modelo";
    private static final String FIELD_WARRANTY_MONTHS = "meses de garantia";
    private static final String FIELD_POWER_CONSUMPTION = "consumo de energia";

    private static final String[] specificFields = {FIELD_MODEL, FIELD_WARRANTY_MONTHS, FIELD_POWER_CONSUMPTION};
    private static Map<String,String> fullFieldAliasesMap;
    private static ArrayList<String> fullStringKeyFields;
    Map<String, Object> fullFieldValuesMap;

    private String model = null;
    private Integer warrantyMonths = null;
    private Double powerConsumption = null;

    public Electronic(String name, String brand,int stock, double basePrice, Scanner scanner){
        super(name, brand, stock, basePrice, scanner);
        fullFieldValuesMap = defineFullValuesMap(specificFields);
        fullStringKeyFields = defineFullKeys(specificFields);
        fullFieldAliasesMap = defineFullAliasesMap(specificFields, fullFieldValuesMap);
    }

    @Override
    public void setSpecificFields() {
        updateModel();
        updateWarrantyMonths();
        updatePowerConsumption();

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
                Modelo: %s
                Meses de garantía: %s
                Consumo de energía: %s
                ********************************************
                """, getId() ,getName(), getBrand(), getStock(), getBasePriceString(), PRODUCT_TYPE,
                getModel(), getWarrantyMonthsString(), getPowerConsumptionString());
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
            case FIELD_MODEL:
                updateModel();
                break;
            case FIELD_WARRANTY_MONTHS:
                updateWarrantyMonths();
                break;
            case FIELD_POWER_CONSUMPTION:
                updatePowerConsumption();
        }
    }

    private void updateModel() {
        updateField(
                FIELD_MODEL,
                this::getModel,
                ConsoleUtils::scanModel,
                this::setModel,
                fullFieldValuesMap
        );
    }

    private void updateWarrantyMonths() {
        updateField(
                FIELD_WARRANTY_MONTHS,
                this::getWarrantyMonths,
                ConsoleUtils::scanWarrantyMonths,
                this::setWarrantyMonths,
                fullFieldValuesMap
        );
    }

    private void updatePowerConsumption() {
        updateField(
                FIELD_POWER_CONSUMPTION,
                this::getPowerConsumption,
                ConsoleUtils::scanPowerConsumption,
                this::setPowerConsumption,
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
        return getBasePrice() * (1 + TaxRates.VAT_GENERAL) * (1 + TaxRates.INTERNAL_TAX_ELECTRONICS);
    }

    public String getModel() {
        if (model == null)
            return UNSIGNED;
        return model;
    }

    public Integer getWarrantyMonths() {
        return warrantyMonths;
    }
    public Double getPowerConsumption() {
        return powerConsumption;
    }

    public void setModel(String model) {
        this.model = StringUtils.titleCase(model);
    }
    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }
    public void setPowerConsumption(double powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public String getPowerConsumptionString(){
        if (powerConsumption == null)
            return UNSIGNED;

        if (powerConsumption < 1000)
            return String.format("%.2f W", powerConsumption);
        else
            return String.format("%.2f kW", powerConsumption/1000);
    }
    public String getWarrantyMonthsString(){
        if (warrantyMonths == null){
            return UNSIGNED;
        }

        return String.valueOf(warrantyMonths);
    }
}
