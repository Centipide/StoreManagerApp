package com.techlab.inicio;

import com.techlab.inicio.order.OrderManager;
import com.techlab.inicio.product.*;
import com.techlab.inicio.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreMenu {
    private static final String EXIT_ACTION_NUMBER = "7";

    private final Scanner scanner = new Scanner(System.in);
    private final ProductManager productManager;
    private final OrderManager orderManager;

    //Se usa String y no int, para poder tener clave tanto númerica como String
    private final Map<String, Runnable> actionsMap = new HashMap<>();



    public StoreMenu() {
        createActionsMap();

        productManager = new ProductManager(scanner);
        orderManager = new OrderManager(scanner);
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

        actionsMap.put("5", this::createOrder);
        actionsMap.put("crear un pedido", this::createOrder);

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
            option = StringUtils.normalizeKey(scanner.nextLine());
            action = actionsMap.get(option);
        }

        System.out.println("Saliendo...");
    }

    private void printOptions(){
        System.out.print("""
                1) Agregar producto
                2) Listar productos
                3) Buscar/Actualizar producto
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

    private void searchUpdateProduct() {
        productManager.searchUpdateProduct();
    }

    private void deleteProduct() {
        productManager.deleteProduct();
    }

    //todo: createOrder debe encargarse de pedirle a prodcutManager el vector products, para saber que guardar dentro
    // deberia guardar el objeto Product de cada producto pedido, junto a su cantidad.

    private void createOrder() {
        //orderManager.createOrder();
    }

    private void listOrder() {
        //orderManager.listOrder();
    }
}
