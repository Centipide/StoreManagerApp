package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductManager {
    private final ArrayList<Product> products = new ArrayList<>();
    private final Scanner scanner;

    private final Map<String, ProductFactory> categoriesFactoryMap = new HashMap<>();
    private final Map<String, String> categoriesNamesMap = new HashMap<>();
    private final Map<String, Product> productsNameMap = new HashMap<>();
    private final Map<Integer, Product> productsIdMap = new HashMap<>();

    public ProductManager(Scanner scanner) {
        createCategoriesFactoryMap();
        createCategoriesNames();

        this.scanner = scanner;
    }

    /** categoriesFactoryMap
     * A partir de categoriesNamesMap obtenemos las claves.
     */
    private void createCategoriesFactoryMap(){
        categoriesFactoryMap.put("bebida", Drink::new);
        categoriesFactoryMap.put("producto empaquetado", PackagedProduct::new);
        categoriesFactoryMap.put("accesorio", Accessory::new);
        categoriesFactoryMap.put("electronico", Electronic::new);
    }

    /** categoriesNamesMap
     * La clave y contenido son idénticos por simplicidad. De esta forma, obtengo
     * la categoría ya sea que el usuario la seleccione ingresando un número o por
     * su nombre.
     */
    private void createCategoriesNames(){
        categoriesNamesMap.put("1", "bebida");
        categoriesNamesMap.put("bebida", "bebida");

        categoriesNamesMap.put("2", "producto empaquetado");
        categoriesNamesMap.put("producto empaquetado", "producto empaquetado");

        categoriesNamesMap.put("3", "accesorio");
        categoriesNamesMap.put("accesorio", "accesorio");

        categoriesNamesMap.put("4", "electronico");
        categoriesNamesMap.put("electronico", "electronico");
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

    //2)
    public void listProducts() {
        if (products.isEmpty()){
            System.out.println("Todavia no se han creado Productos");
            return;
        }
        for (Product product: products)
            product.print();
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
        String categoryName = categoriesNamesMap.get(option);

        while (categoryName == null){
            System.out.println("Opción inválida, Ingrese nuevamente");
            printCategories();
            option = scanner.nextLine().trim().toLowerCase();
            categoryName = categoriesNamesMap.get(option);
        }
        return categoryName;
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

    public Product searchProduct(){
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
        Product product = null;

        product = findByName(key);
        if (product != null)
            return product;

        product = findById(key);
        return product;
    }

    private Product findById(String key){
        Product product = null;
        try {
            int id = Integer.parseInt(key);
            product = productsIdMap.get(id);
        } catch (NumberFormatException e) {
        }
        return product;
    }

    private Product findByName(String key){
        return productsNameMap.get(key);
    }

    private String scanProductKey(){
        System.out.print("Ingrese el Nombre o ID del producto (o salir para cancelar): ");
        String key = scanner.nextLine().trim();

        while (key.isEmpty()){
            System.out.println("Error, ingrese nuevamente");
            System.out.print("Nombre o ID: ");
            key = scanner.nextLine().trim();
        }

        return StringUtils.normalizeKey(key);
    }


    public ArrayList<Product> getProducts(){
        return products;
    }

    public Map<String, Product> getProductsNameMap(){
        return productsNameMap;
    }

    public Map<Integer, Product> getProductsIdMap(){
        return productsIdMap;
    }
}
