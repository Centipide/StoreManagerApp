package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductManager {
    private final ArrayList<Product> products = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private final Map<String, ProductFactory> categoriesFactoryMap;
    private final Map<String, String> categoriesNames;
    private final Map<String, Product> productsNameMap = new HashMap<>();
    private final Map<Integer, Product> productsIdMap = new HashMap<>();

    public ProductManager(Map<String, ProductFactory> categoriesFactoryMap, Map<String, String> categoriesNames) {
        this.categoriesFactoryMap = categoriesFactoryMap;
        this.categoriesNames = categoriesNames;
    }

    //1)
    public void addProduct(){
        System.out.println("Ingrese el nuevo Producto: ");
        String name = ConsoleUtils.scanName(scanner);
        double basePrice = ConsoleUtils.scanBasePrice(scanner);
        int stock = ConsoleUtils.scanStock(scanner);
        String categoryName = scanCategoryName(); //también sirve como key para categoriesFactoryMap
        ProductFactory productFactory = categoriesFactoryMap.get(categoryName);

        if (productFactory != null) {
            Product newProduct = productFactory.create(name, stock, basePrice);
            products.add(newProduct);
            addProductMap(newProduct);
            System.out.println("Producto agregado exitosamente.");
        } else {
            System.out.println("Error al crear producto.");
        }
    }

    private void addProductMap(Product product){
        productsNameMap.put(StringUtils.normalizeKey((product.getName())), product);
        productsIdMap.put(product.getId(), product);
    }

    public void updateProductsNameMap(String oldKey, String newKey){
        Product product = productsNameMap.get(oldKey);
        if (product == null){
            System.out.println("ERROR al buscar producto con oldKey");
            return;
        }
        productsNameMap.remove(oldKey);
        productsNameMap.put(StringUtils.normalizeKey(newKey), product);
    }

    private void removeProduct(String key){
        Product product = productsNameMap.get(key);
        productsNameMap.remove(key);
        products.remove(product);
    }


    private void printCategories() {
        System.out.print("""
                1) Bebida
                2) Producto Empaquetado
                3) Accesorio
                4) Electrónico
                """);
        System.out.print("\nElija una opción: ");
    }

    private String scanCategoryName(){
        printCategories();
        String option = scanner.nextLine().trim().toLowerCase();
        String categoryName = categoriesNames.get(option);

        while (categoryName == null){
            System.out.println("Opción inválida, Ingrese nuevamente");
            printCategories();
            option = scanner.nextLine().trim().toLowerCase();
            categoryName = categoriesNames.get(option);
        }
        return categoryName;
    }


    //2)
    public void listProducts() {
        for (Product product: products){
            product.print();
        }
    }

    //3)
    /**
     * El sistema permitirá buscar un producto por su nombre o ID.Si se encuentra el producto,
     * se mostrará su información completa.
     * Opcionalmente, se podrá actualizar alguno de sus datos (precio o stock),
     * validando que los valores sean coherentes (por ejemplo, que el stock no sea negativo).
     */
    public void searchUpdateProduct() {
        Product searchedProduct = searchProduct();
        if (searchedProduct == null) //si es null, se ha ingresado salir
            return;

        searchedProduct.print();
        updateProduct(searchedProduct);
    }

    private void updateProduct(Product product){
        if (!ConsoleUtils.confirm(scanner,"Desea actualizar algun campo?"))
            return;

        String selectedField = selectField(product);
        product.updateField(this, scanner, selectedField);
    }

    private String selectField(Product product){
        product.printFullFields();
        System.out.print("Ingrese el campo a seleccionar: ");
        String input = scanner.nextLine();
        String selectedField = product.getField(input);

        while (selectedField == null){
            System.out.println("Error, Campo inexistente");
            System.out.print("Ingrese el campo a seleccionar: ");
            input = scanner.nextLine();
            selectedField = product.getField(input);
        }

        return selectedField;
    }

    private Product searchProduct(){
        String key = scanProductKey();
        if (key.equalsIgnoreCase("salir"))
            return null;
        Product searchedProduct = obtainProduct(key);

        while (searchedProduct == null){
            System.out.println("Producto no encontrado");
            key = scanProductKey();
            if (key.equalsIgnoreCase("salir"))
                return null;
            searchedProduct = obtainProduct(key);
        }

        return searchedProduct;
    }

    // todo: revisar trycatch. la funcion busca por nombre primero, y sino por ID
    private Product obtainProduct(String key) {
        if (key == null || key.isEmpty()){
            System.out.println("Error: No se encontró key");
            return null;
        }

        Product product = productsNameMap.get(key);
        if (product != null)
            return product;

        try {
            int id = Integer.parseInt(key);
            product = productsIdMap.get(id);
        } catch (NumberFormatException e) {
        }

        return product;
    }

    private String scanProductKey(){
        System.out.print("Ingrese el Nombre o ID del producto (o salir para volver atrás): ");
        String key = scanner.nextLine().trim();

        while (key.isEmpty()){
            System.out.println("Error, ingrese nuevamente");
            System.out.print("Nombre o ID: ");
            key = scanner.nextLine().trim();
        }

        return StringUtils.normalizeKey(key);
    }


    //4)
    public void deleteProduct(){
        String key = scanProductKey();

        if (obtainProduct(key) != null)
            removeProduct(key);
        else{
            System.out.println("Producto no encontrado");
            return;
        }

        System.out.println("Producto " + key + " borrado exitosamente");
    }
}
