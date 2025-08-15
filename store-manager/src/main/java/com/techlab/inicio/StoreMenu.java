package com.techlab.inicio;

import com.techlab.inicio.order.OrderManager;
import com.techlab.inicio.product.ProductManager;
import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreMenu {
    private static final String ADD_PRODUCT = "agregar producto";
    private static final String LIST_PRODUCTS = "listar productos";
    private static final String SEARCH_UPDATE_PRODUCTS = "buscar / actualizar producto";
    private static final String DELETE_PRODUCT = "eliminar producto";
    private static final String CREATE_ORDER = "crear un pedido";
    private static final String LIST_ORDERS = "listar pedidos";
    private static final String DELETE_ORDER = "eliminar un pedido";
    private static final String EXIT = "salir";

    private final Runnable[] runnableActions;
    private final String[] stringKeyActions;

    private final Scanner scanner = new Scanner(System.in);
    private final Map<String, Runnable> actionsMap = new HashMap<>();
    private boolean exitFlag = false;


    public StoreMenu() {
        ProductManager productManager = new ProductManager(scanner);
        OrderManager orderManager = new OrderManager(scanner, productManager);

        runnableActions = new Runnable[]{
                productManager::addProduct, productManager::listProducts, productManager::searchUpdateProduct,
                productManager::deleteProduct, orderManager::createOrder, orderManager::listOrders,
                orderManager::deleteOrder, () -> exitFlag = true
        };

        stringKeyActions = new String[]{
                ADD_PRODUCT, LIST_PRODUCTS, SEARCH_UPDATE_PRODUCTS, DELETE_PRODUCT,
                CREATE_ORDER, LIST_ORDERS, DELETE_ORDER, EXIT
        };

        createActionsMap();
    }

    public void run(){
        do{
            printOptions();
            String option = scanner.nextLine().trim().toLowerCase();
            Runnable action = actionsMap.get(option);

            if (action != null)
                action.run();

            ConsoleUtils.clearScreen();

            if (action == null)
                System.out.println("Opción inválida, Ingrese nuevamente");
        } while (!exitFlag);

        System.out.println("Saliendo...");
    }

    private void printOptions(){
        for (int i = 0; i < stringKeyActions.length; i++){
            System.out.printf("%d) %s\n", i + 1 ,StringUtils.titleCase(stringKeyActions[i]));
        }
        System.out.print("Elija una opción (ingrese número o comando): ");
    }

    private void createActionsMap() {
        for (int i = 0; i < runnableActions.length; i++) {
            actionsMap.put(stringKeyActions[i], runnableActions[i]);
            actionsMap.put(String.valueOf(i + 1), runnableActions[i]);
        }
    }
}
