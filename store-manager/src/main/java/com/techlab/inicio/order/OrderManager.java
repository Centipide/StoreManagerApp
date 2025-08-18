package com.techlab.inicio.order;

import com.techlab.inicio.product.ProductManager;
import com.techlab.inicio.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderManager {
    private final Scanner scanner;
    private final ProductManager productManager;
    private final ArrayList<Order> orders = new ArrayList<>();
    private static final int NOT_FOUND = -1;

    public OrderManager(Scanner scanner, ProductManager productManager){
        this.scanner = scanner;
        this.productManager = productManager;
    }

    public void createOrder(){
        if (productManager.getProducts().isEmpty()){
            System.out.println("Todavia no se han creado productos");
            return;
        }

        int amountOfDifProducts = ConsoleUtils.scanAmountOfDifProducts(scanner);
        Order order = new Order(scanner, productManager, amountOfDifProducts);

        if (order.createOrderLines()) // si cancelan la nueva orden, no se guarda
            orders.add(order);
        else
            System.out.println("Pedido cancelado");
    }

    public void listOrders(){
        if (orders.isEmpty()){
            System.out.println("No se han cargado pedidos");
            return;
        }
        for (Order order: orders)
            order.print();
    }

    public void deleteOrder(){
        if (orders.isEmpty()){
            System.out.println("No se han cargado pedidos");
            return;
        }
        listOrders();
        boolean invalid = true;
        int position;

        do{
            System.out.println("Ingrese el ID de la orden a eliminar: ");
            int orderIDToDelete = ConsoleUtils.scanOrderIDToDelete(scanner);

            position = findOrderById(orders, orderIDToDelete);
            if (position == NOT_FOUND){
                System.out.println("No se encontró una orden con ese ID. Intente de nuevo.");
            } else{
                invalid = false;
            }
        } while (invalid);

        orders.remove(position);
        System.out.println("Orden eliminada correctamente");
    }

    int findOrderById(ArrayList<Order> orders, int id){
        for(Order order: orders){
            if (id == order.getId()){
                return orders.indexOf(order);
            }
        }

        return NOT_FOUND;
    }
}
