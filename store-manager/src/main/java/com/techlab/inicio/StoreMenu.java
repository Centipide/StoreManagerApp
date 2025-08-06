package com.techlab.inicio;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StoreMenu {
    private Scanner scanner = new Scanner(System.in);
    private static final int MIN_OPTION = 1;
    private static final int MAX_OPTION = 7;
    private static final int EXIT_OPTION = 7;
    private final Map<Integer, Runnable> actions = new HashMap<>();



    public StoreMenu(){
        actions.put(1, this::addProduct); //pasar a ingles
        actions.put(2, this::listProducts);
        actions.put(3, this::searchUpdateProducts);
        actions.put(4, this::deleteProduct);
        actions.put(5, this::createOrderLine);
        actions.put(6, this::listOrder);
        actions.put(7, () -> System.out.println("Saliendo del sistema..."));
    }

    public void run(){
        int option;

        do {
            option = getOption();
            Runnable action = actions.get(option);

            if (action != null)
                action.run(); //si consiguio alguna función, la ejecuta (incluyendo sout)
            else
                System.out.println("Opción inválida");

        } while (option != EXIT_OPTION);
    }

    private void printOptions(){
        System.out.println("""
                1) Agregar producto
                2) Listar productos
                3) Buscar/Actualizar producto
                4) Eliminar producto
                5) Crear un pedido
                6) Listar pedidos
                7) Salir
                
                Elija una opción:
                """);
    }

    private int getOption(){
        int option;
        boolean valid = true;

        do{
            if (!valid)
                System.out.println("Error, ingrese nuevamente el número");
            valid = false;

            printOptions();
            option = scanner.nextInt();
            scanner.nextLine();
        } while (option < MIN_OPTION || option > MAX_OPTION);

        return option;
    }

    public void addProduct(){
        System.out.println(" ");
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
