package com.techlab.inicio.product;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class ProductManager {
    private final ArrayList<Product> products = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);

    private final Map<String, ProductFactory> categoriesFactoryMap;
    private final Map<String, String> categoriesNames;

    public ProductManager(Map<String, ProductFactory> categoriesFactoryMap, Map<String, String> categoriesNames) {
        this.categoriesFactoryMap = categoriesFactoryMap;
        this.categoriesNames = categoriesNames;
    }

    //1)
    public void addProduct(){
        System.out.println("Ingrese el nuevo Producto: ");
        String name = scanName();
        double basePrice = scanBasePrice();
        int stock = scanStock();
        String categoryName = scanCategoryName(); //también sirve como key para categoriesFactoryMap
        ProductFactory productFactory = categoriesFactoryMap.get(categoryName);

        if (productFactory != null) {
            Product newProduct = productFactory.create(name, stock, basePrice);
            products.add(newProduct);
            System.out.println("Producto agregado exitosamente.");
        } else {
            System.out.println("Error al crear producto.");
        }
    }

    private String scanName(){
        System.out.print("Nombre: ");
        String name = scanner.nextLine(); //todo: controlar correctamente

        return name;
    }

    private double scanBasePrice(){
        System.out.print("Precio base: ");
        double basePrice = scanner.nextDouble();
        scanner.nextLine();
        while (basePrice < 0){
            System.out.println("Ingrese un precio base válido");
            System.out.print("Precio base: ");
            basePrice = scanner.nextDouble();
            scanner.nextLine();
        }

        return basePrice;
    }

    private int scanStock(){
        System.out.print("Ingrese cantidad de stock: ");
        int stock = scanner.nextInt();
        scanner.nextLine();
        while (stock < 0){
            System.out.println("Ingrese una cantidad de stock válida");
            System.out.print("Ingrese cantidad de stock: ");
            stock = scanner.nextInt();
            scanner.nextLine();
        }

        return stock;
    }

    private String scanCategoryName(){
        printCategories();
        String option = scanner.nextLine().trim().toLowerCase();
        String categoryName = categoriesNames.get(option);

        while (categoryName == null){
            System.out.println("Opción inválida, Ingrese nuevamente");
            printCategories();
            option = scanner.nextLine().trim().toLowerCase();
            categoryName = categoriesNames.get(option);
        }
        return categoryName;
    }

    private void printCategories() {
        System.out.print("""
                1) Bebida
                2) Producto Empaquetado
                3) Accesorio
                4) Electrónico
                
                """);
        System.out.print("\nElija una opción: ");
    }


    //2)
    public void listProducts() {
        for (Product product: products){
            product.print();
        }
    }

    //3)

    public void searchUpdateProduct() {

    }

    //4)
    public void deleteProduct(){

    }
}
