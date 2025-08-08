package com.techlab.inicio.product;

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
        String name = scanName();
        double basePrice = scanBasePrice();
        int stock = scanStock();
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
        productsNameMap.put(normalizeKey(product.getName()), product);
        productsIdMap.put(product.getId(), product);
    }

    private String scanName(){
        System.out.print("Nombre: ");
        String name = scanner.nextLine().trim();

        while (name.isEmpty()){
            System.out.println("Error, ingrese nuevamente");
            System.out.print("Nombre: ");
            name = scanner.nextLine().trim();
        }

        return name;
    }

    private double scanBasePrice(){
        System.out.print("Precio base: ");
        double basePrice = scanner.nextDouble();
        scanner.nextLine();
        while (basePrice < 0){
            System.out.println("Ingrese un precio base válido");
            System.out.print("Precio base: ");
            basePrice = scanner.nextDouble();
            scanner.nextLine();
        }

        return basePrice;
    }

    private int scanStock(){
        System.out.print("Ingrese cantidad de stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        while (stock < 0){
            System.out.println("Ingrese una cantidad de stock válida");
            System.out.print("Ingrese cantidad de stock: ");
            stock = scanner.nextInt();
            scanner.nextLine();
        }

        return stock;
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

    private void printCategories() {
        System.out.print("""
                1) Bebida
                2) Producto Empaquetado
                3) Accesorio
                4) Electrónico
                """);
        System.out.print("\nElija una opción: ");
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

    /* TODO: en Producto, agregar una funcion que sea PrintFields. Cada categoria de producto debe mostrar sus
          campos enumerados, de forma que el usuario pueda ingresar un numero o String(nombre del campo).

          todo: cada categoría debería tener un hashmap que permita idenitficar con numero y string el campo
            Selectedfield deberia tener lo ingresado por el usuario. A partir de ello se accede al hashmap
            de la categoria respondiente. El hashmap deberia iniciar una funcion como "updatePrice" o diferente
            dependiendo de selectedField. Estas funciones estan en cada categoria particular (con excepcion de
            updatePrice, updateName, updateStock que son comunes a todos, deberian estar en Product)

            todo: cada funcion tiene que pedir un valor y actualizarlo, verificando que sea válido.
     */
    private void updateProduct(Product searchedProduct){
        if (!confirm("Desea actualizar algun campo?"))
            return;

        searchedProduct.printFields();
        selectedField = selectField(searchedProduct);

    }

    private boolean confirm(String msg){
        System.out.println(msg + "(S/N): ");
        String input = scanner.nextLine().trim().toLowerCase();

        while (!input.equals("s") && !input.equals("n")) {
            System.out.print("Por favor, ingrese 's' o 'n': ");
            input = scanner.nextLine().trim().toLowerCase();
        }

        return input.equals("s");
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

        return normalizeKey(key);
    }

    private String normalizeKey(String key){
        return key.toLowerCase().trim();
    }


    //4)
    public void deleteProduct(){

    }
}
