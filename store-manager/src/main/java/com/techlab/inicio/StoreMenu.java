package com.techlab.inicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreMenu {
    private static final String EXIT_ACTION_NUMBER = "7";
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Product> products = new ArrayList<>();

    //Se usa String y no int, para poder tener clave tanto númerica como String
    private final Map<String, Runnable> actionsMap = new HashMap<>();
    private final Map<String, ProductFactory> categoriesFactoryMap = new HashMap<>();
    private final Map<String, String> categoriesNames = new HashMap<>();

    public StoreMenu() {
        //** actionsMap **
        actionsMap.put("1", this::addProduct);
        actionsMap.put("agregar producto", this::addProduct);

        actionsMap.put("2", this::listProducts);
        actionsMap.put("listar productos", this::listProducts);

        actionsMap.put("3", this::searchUpdateProducts);
        actionsMap.put("actualizar producto", this::searchUpdateProducts); //buscar/actualizar

        actionsMap.put("4", this::deleteProduct);
        actionsMap.put("eliminar producto", this::deleteProduct);

        actionsMap.put("5", this::createOrderLine);
        actionsMap.put("crear un pedido", this::createOrderLine);

        actionsMap.put("6", this::listOrder);
        actionsMap.put("listar pedidos", this::listOrder);

        actionsMap.put("7", () -> {});
        actionsMap.put("salir", () -> {});

        /** categoriesNames
         * La clave y contenido son idénticos por simplicidad. De esta forma, obtengo
         * la categoría ya sea que el usuario la seleccione ingresando un número o por
         * su nombre.
         */
        categoriesNames.put("1", "bebida");
        categoriesNames.put("bebida", "bebida");

        categoriesNames.put("2", "producto empaquetado");
        categoriesNames.put("producto empaquetado", "producto empaquetado");

        categoriesNames.put("3", "accesorio");
        categoriesNames.put("accesorio", "accesorio");

        categoriesNames.put("4", "electronico");
        categoriesNames.put("electronico", "electronico");

        /** categoriesFactoryMap
         * A partir de categoriesNames obtenemos las claves.
         */
        categoriesFactoryMap.put("bebida", Drink::new);
        categoriesFactoryMap.put("producto empaquetado", PackagedProduct::new);
        categoriesFactoryMap.put("accesorio", Accessory::new);
        categoriesFactoryMap.put("electronico", Electronic::new);

    }

    public void run(){
        printOptions();
        String option = scanner.nextLine().trim().toLowerCase();
        Runnable action = actionsMap.get(option);

        while (!option.equals("salir") && !option.equals(EXIT_ACTION_NUMBER)){
            if (action != null)
                action.run();
            else{
                System.out.println("Opción inválida, Ingrese nuevamente");
            }

            printOptions();
            option = scanner.nextLine().trim().toLowerCase();
            action = actionsMap.get(option);
        }

        System.out.println("Saliendo...");
    }

    private void printOptions(){
        System.out.print("""
                1) Agregar producto
                2) Listar productos
                3) Actualizar producto
                4) Eliminar producto
                5) Crear un pedido
                6) Listar pedidos
                7) Salir
                """);

        System.out.print("\nElija una opción: ");
    }

    /**
     * Lee los datos y crea un nuevo Producto.
     */
    private void addProduct(){
        System.out.println("Ingrese el nuevo Producto: ");
        String name = scanName();
        double basePrice = scanBasePrice();
        int stock = scanStock();
        String categoryName = scanCategoryName(); //también sirve como key para categoriesFactoryMap
        ProductFactory productFactory = categoriesFactoryMap.get(categoryName);

        if (productFactory != null) {
            Product newProduct = productFactory.create(name, stock, basePrice);
            products.add(newProduct);
            System.out.println("Producto agregado exitosamente.");
        } else {
            System.out.println("Error al crear producto.");
        }
    }

    private String scanName(){
        System.out.print("Nombre: ");
        String name = scanner.nextLine(); //todo: controlar correctamente

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
    private void listProducts() {
        for (Product product: products){
            product.print();
        }
    }

    private void listOrder() {
    }

    private void createOrderLine() {
    }

    private void deleteProduct() {
    }

    private void searchUpdateProducts() {
    }
}
