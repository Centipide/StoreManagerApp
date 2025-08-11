package com.techlab.inicio.order;

import com.techlab.inicio.product.ProductManager;
import com.techlab.inicio.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class OrderManager {
    private final Scanner scanner;
    private final ProductManager productManager;
    private final ArrayList<Order> orders = new ArrayList<>();

    public OrderManager(Scanner scanner, ProductManager productManager){
        this.scanner = scanner;
        this.productManager = productManager;
    }

    public void createOrder(){
        int amountOfDifProducts = ConsoleUtils.scanAmountOfDifProducts(scanner);
        Order order = new Order(scanner, productManager, amountOfDifProducts);

        if (order.createOrderLines()) // si cancelan la nueva orden, no se guarda
            orders.add(order);
        else
            System.out.println("Pedido cancelado");
    }

    public void listOrders(){
        for (Order order: orders)
            order.print();
    }
}
