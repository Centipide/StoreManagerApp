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

    //todo: se puede mejorar para que no tenga que ingresar todo el producto de nuevo si no hay stock
    public boolean createOrderLines() {
        productManager.listProducts();
        ArrayList<OrderLine> tempOrderLines = new ArrayList<>();
        int i = 0;

        System.out.println("Ingrese el/los productos para agregar a la orden:");
        while (i < amountOfDifProducts) {
            Product product = productManager.searchProduct();

            if (product == null) { // si el usuario escribe "salir"
                cancelCreation();
                return false;
            }

            int requestedUnits = ConsoleUtils.scanUnits(scanner);
            int availableStock = product.getStock();

            if (requestedUnits <= availableStock){
                OrderLine tempOrderLine = new OrderLine(product, requestedUnits);
                OrderLine existing = orderLineExists(tempOrderLines, product); //devuelve null si no existe

                if (existing == null){
                    addNewOrderLine(tempOrderLines, tempOrderLine);
                } else{
                    fusionOrderLines(tempOrderLines, tempOrderLine);
                }

                product.decreaseStock(requestedUnits);
                i++;
            } else{
                printNoStock(availableStock);
            }
        }

        orderLines = tempOrderLines;
        return true;
    }

    private void addNewOrderLine(ArrayList<OrderLine> tempOrderLines, OrderLine orderLine){
        tempOrderLines.add(orderLine);
    }

    private void fusionOrderLines(ArrayList<OrderLine> tempOrderLines, OrderLine orderLine){
        for (OrderLine ol : tempOrderLines){
            if (ol.getProduct().getId() == orderLine.getProduct().getId()){
                ol.increaseUnits(orderLine.getUnits());
                break;
            }
        }
    }

    private OrderLine orderLineExists(ArrayList<OrderLine> tempOrderLines, Product product){
        for (OrderLine orderLine: tempOrderLines){
            if (orderLine.getProduct().getId() == product.getId()) {
                return orderLine;
            }
        }

        return null;
    }

    private void printNoStock(int availableStock){
        System.out.println("No hay stock suficiente para este pedido.");
        System.out.println("Stock disponible: " + availableStock + " unidades.");
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
