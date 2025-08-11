package com.techlab.inicio.utils;

import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class ConsoleUtils {
    public static boolean confirm(Scanner scanner, String msg){
        System.out.print(msg + "(S/N): ");
        String input = scanner.nextLine().trim().toLowerCase();

        while (!input.equals("s") && !input.equals("n")) {
            System.out.print("Por favor, ingrese 's' o 'n': ");
            input = scanner.nextLine().trim().toLowerCase();
        }

        return input.equals("s");
    }

    public static String scanName(Scanner scanner){
        System.out.print("Nombre: ");
        String name = scanner.nextLine().trim();

        while (name.isEmpty()){
            System.out.println("Error, ingrese nuevamente");
            System.out.print("Nombre: ");
            name = scanner.nextLine().trim();
        }

        return name;
    }

    public static double scanBasePrice(Scanner scanner){
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

    public static int scanStock(Scanner scanner){
        System.out.print("Ingrese cantidad de stock: ");
        return scanIntGreaterThan(scanner, 0, "Ingrese una cantidad de stock válida: ");
    }

    public static int scanAmountOfDifProducts(Scanner scanner){
        System.out.print("Ingrese cantidad de productos diferentes: ");
        return scanIntGreaterThan(scanner, 1, "Ingrese una cantidad de productos valida: ");
    }

    public static int scanUnits(Scanner scanner){
        System.out.print("Ingrese cantidad de unidades del Producto: ");
        return scanIntGreaterThan(scanner, 1, "Ingrese una cantidad de unidades valida: ");
    }

    public static int scanIntGreaterThan(Scanner scanner, int minLimit, String errMsg){
        int integer = scanner.nextInt();
        scanner.nextLine();

        while (!(integer >= minLimit)){
            System.out.print(errMsg);
            integer = scanner.nextInt();
            scanner.nextLine();
        }

        return integer;
    }

    public static void showUpdate(String field, String oldField, String newField){
        System.out.printf("%s: %s Cambiado a %s\n",
                StringUtils.titleCase(field),
                StringUtils.titleCase(oldField),
                StringUtils.titleCase(newField));
    }
}
