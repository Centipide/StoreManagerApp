package com.techlab.inicio;

import com.techlab.inicio.order.OrderManager;
import com.techlab.inicio.product.*;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
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
        productManager = new ProductManager(scanner);
        orderManager = new OrderManager(scanner, productManager);

        createActionsMap();
    }

    private void createActionsMap() {
        actionsMap.put("1", productManager::addProduct);
        actionsMap.put("agregar producto", productManager::addProduct);

        actionsMap.put("2", productManager::listProducts);
        actionsMap.put("listar productos", productManager::listProducts);

        actionsMap.put("3", productManager::searchUpdateProduct);
        actionsMap.put("actualizar producto", productManager::searchUpdateProduct);

        actionsMap.put("4", productManager::deleteProduct);
        actionsMap.put("eliminar producto", productManager::deleteProduct);

        actionsMap.put("5", orderManager::createOrder);
        actionsMap.put("crear un pedido", orderManager::createOrder);

        actionsMap.put("6", orderManager::listOrders);
        actionsMap.put("listar pedidos", orderManager::listOrders);

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
}
