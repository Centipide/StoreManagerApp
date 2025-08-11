package com.techlab.inicio.order;

import com.techlab.inicio.product.Product;
import com.techlab.inicio.product.ProductManager;
import com.techlab.inicio.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Scanner;

public class Order {
    private final Scanner scanner;
    private final ProductManager productManager;
    private ArrayList<OrderLine> orderLines = new ArrayList<>();
    private final int amountOfDifProducts;

    private final int id;
    private static int counterId = 0;

    {
        counterId++;
        id = counterId;
    }

    public Order(Scanner scanner, ProductManager productManager, int amountOfDifProducts){
        this.scanner = scanner;
        this.productManager = productManager;
        this.amountOfDifProducts = amountOfDifProducts;
    }

    public boolean createOrderLines() {
        productManager.listProducts();
        ArrayList<OrderLine> tempOrderLines = new ArrayList<>();

        System.out.println("Ingrese el/los productos para agregar a la orden:");
        for (int i = 0; i < amountOfDifProducts; i++) {
            Product product = productManager.searchProduct();

            if (product == null) { // si el usuario escribe "salir"
                cancelCreation();
                return false;
            }

            int units = ConsoleUtils.scanUnits(scanner);
            tempOrderLines.addLast(new OrderLine(product, units));
        }

        orderLines = tempOrderLines;
        return true;
    }

    private void cancelCreation(){
        counterId--;
    }

    public void print() {
        System.out.printf("""
                ===========================================
                Orden: %d\n
                """, this.id);

        for (OrderLine orderLine: orderLines){
            orderLine.print();
        }
        System.out.printf("===========================================\n");
    }
}
