package com.techlab.inicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreMenu {
    private static final int MIN_ACTION = 1;
    private static final int MAX_ACTION = 7;
    private static final String EXIT_ACTION_NUMBER = "7";
    private static final int MIN_CATEGORY = 1;
    private static final int MAX_CATEGORY = 4;
    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Product> products= new ArrayList<>();

    //Se usa String y no int, para poder tener clave tanto númerica como String
    private final Map<String, Runnable> actionsMap = new HashMap<>();
    private final Map<String, Runnable> categoriesMap= new HashMap<>();


    public StoreMenu() {
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
    }

    public void run(){
        printOptions();
        String option = scanner.nextLine();
        Runnable action = actionsMap.get(option);

        while (!option.equals("salir") && !option.equals(EXIT_ACTION_NUMBER)){
            if (action != null)
                action.run();
            else{
                System.out.println("Opción inválida, Ingrese nuevamente");
                printOptions();
            }


            option = scanner.nextLine();
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
                
                Elija una opción: 
                """);
    }

    /**
     * Lee los datos y crea un nuevo Producto.
     */
    public void addProduct(){
        System.out.println("Ingrese el nuevo Producto: ");
        String name = scanName();
        double basePrice = scanBasePrice();
        int stock = scanStock();
        String category = scanCategory();

    }

    private String scanName(){
        System.out.print("Nombre: ");
        String name = scanner.nextLine(); //todo: controlar correctamente
        System.out.println();

        return name;
    }

    private double scanBasePrice(){
        System.out.print("Precio base: ");
        double basePrice = scanner.nextDouble();
        scanner.nextLine();
        while (basePrice < 0){
            System.out.println("Ingrese un precio base válido");
            basePrice = scanner.nextDouble();
            scanner.nextLine();
        }

        return basePrice;
    }

    private int scanStock(){
        System.out.println("Ingrese cantidad de stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        while (stock < 0){
            System.out.println("Ingrese una cantidad de stock válida");
            stock = scanner.nextInt();
            scanner.nextLine();
        }

        return stock;
    }

    private String scanCategory(){
        printCategories();
        System.out.print("Categoria: ");
        String category = scanner.nextLine(); //todo: controlar correctamente
        System.out.println();

        return category;
    }

    //private final String[] categories = {"Drink","Packaged Product", "Accessory", "Electronic"};

    private void printCategories() {
        System.out.println("""
                1) Bebida
                2) Producto Empaquetado
                3) Accesorio
                4) Electrónico
                
                Elija una opción:
                """);
    }

    private void listOrder() {
    }

    private void createOrderLine() {
    }

    private void deleteProduct() {
    }

    private void searchUpdateProducts() {
    }

    private void listProducts() {
    }
}
