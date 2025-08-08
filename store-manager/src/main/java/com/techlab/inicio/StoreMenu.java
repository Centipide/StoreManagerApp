package com.techlab.inicio;

import com.techlab.inicio.product.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreMenu {
    private static final String EXIT_ACTION_NUMBER = "7";

    private final Scanner scanner = new Scanner(System.in);
    private final ProductManager productManager;

    //Se usa String y no int, para poder tener clave tanto númerica como String
    private final Map<String, Runnable> actionsMap = new HashMap<>();
    private final Map<String, ProductFactory> categoriesFactoryMap = new HashMap<>();
    private final Map<String, String> categoriesNames = new HashMap<>();



    public StoreMenu() {
        createActionsMap();
        createCategoriesNames();
        createCategoriesFactoryMap();

        productManager = new ProductManager(categoriesFactoryMap, categoriesNames);
    }

    /** categoriesFactoryMap
     * A partir de categoriesNames obtenemos las claves.
     */
    private void createCategoriesFactoryMap(){
        categoriesFactoryMap.put("bebida", Drink::new);
        categoriesFactoryMap.put("producto empaquetado", PackagedProduct::new);
        categoriesFactoryMap.put("accesorio", Accessory::new);
        categoriesFactoryMap.put("electronico", Electronic::new);
    }

    /** categoriesNames
     * La clave y contenido son idénticos por simplicidad. De esta forma, obtengo
     * la categoría ya sea que el usuario la seleccione ingresando un número o por
     * su nombre.
     */
    private void createCategoriesNames(){
        categoriesNames.put("1", "bebida");
        categoriesNames.put("bebida", "bebida");

        categoriesNames.put("2", "producto empaquetado");
        categoriesNames.put("producto empaquetado", "producto empaquetado");

        categoriesNames.put("3", "accesorio");
        categoriesNames.put("accesorio", "accesorio");

        categoriesNames.put("4", "electronico");
        categoriesNames.put("electronico", "electronico");
    }

    private void createActionsMap() {
        actionsMap.put("1", this::addProduct);
        actionsMap.put("agregar producto", this::addProduct);

        actionsMap.put("2", this::listProducts);
        actionsMap.put("listar productos", this::listProducts);

        actionsMap.put("3", this::searchUpdateProduct);
        actionsMap.put("actualizar producto", this::searchUpdateProduct);

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
        String option = scanner.nextLine().trim().toLowerCase();
        Runnable action = actionsMap.get(option);

        while (!option.equalsIgnoreCase("salir") && !option.equals(EXIT_ACTION_NUMBER)){
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


    private void addProduct(){
        productManager.addProduct();
    }

    private void listProducts() {
        productManager.listProducts();
    }

    private void listOrder() {
    }

    private void createOrderLine() {
    }

    private void deleteProduct() {
        productManager.deleteProduct();
    }

    private void searchUpdateProduct() {
        productManager.searchUpdateProduct();
    }
}
