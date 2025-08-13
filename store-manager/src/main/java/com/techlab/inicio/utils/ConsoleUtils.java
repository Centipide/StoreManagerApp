package com.techlab.inicio.utils;

import java.nio.channels.ScatteringByteChannel;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleUtils {
    public static boolean confirm(Scanner scanner, String msg){
        String input;
        boolean invalid = true;

        do{
            System.out.print(msg + "(S/N): ");
            input = scanner.nextLine().trim().toLowerCase();

            if (!input.equals("s") && !input.equals("n")){
                System.out.print("Por favor, ingrese 'S' o 'N'");
            }  else{
                invalid = false;
            }
        } while (invalid);

        return input.equals("s");
    }


    public static String scanName(Scanner scanner) {
        String name;
        do {
            System.out.print("Nombre: ");
            name = scanner.nextLine().trim();

            String error = validateName(name);
            if (error != null) {
                System.out.println(error);
                name = null;
            }

        } while (name == null);

        return name;
    }

    private static String validateName(String name) {
        if (name.isEmpty()) {
            return "Error, ingrese nuevamente";
        }
        if (name.matches("\\d+")) {
            return "Error, el nombre no puede tener solo números";
        }
        return null;
    }

    public static double scanBasePrice(Scanner scanner){
        double basePrice = -1;

        while (basePrice < 0){
            System.out.print("Precio base: ");
            try{
                basePrice = scanner.nextDouble();
                scanner.nextLine();

                if (basePrice < 0){
                    System.out.println("Ingrese un precio base valido");
                }
            } catch (InputMismatchException e){
                System.out.println("Error, ingrese un número valido");
                scanner.nextLine();
            }
        }

        return basePrice;
    }

    public static int scanStock(Scanner scanner){
        return scanIntGreaterThan(scanner, 0,"Ingrese cantidad de stock: " ,"Ingrese una cantidad de stock válida: ");
    }

    public static int scanAmountOfDifProducts(Scanner scanner){
        return scanIntGreaterThan(scanner, 1,"Ingrese cantidad productos a agregar al pedido: ", "Ingrese una cantidad de productos valida: ");
    }

    public static int scanUnits(Scanner scanner){
        return scanIntGreaterThan(scanner, 1, "Ingrese cantidad de unidades del producto: ","Ingrese una cantidad de unidades valida: ");
    }

    public static int scanIntGreaterThan(Scanner scanner, int minLimit,String requestMsg ,String errMsg){
        int integer = minLimit-1;

        while (!(integer >= minLimit)){
            System.out.print(requestMsg);

            try{
                integer = scanner.nextInt();
                scanner.nextLine();

                if (!(integer >= minLimit)){
                    System.out.println(errMsg);
                }
            } catch (InputMismatchException e){
                System.out.println("Error, ingrese un número válido");
                scanner.nextLine();
            }
        }

        return integer;
    }

    public static void showUpdate(String field, String oldField, String newField){
        System.out.printf("%s: %s Cambiado a %s\n",
                StringUtils.titleCase(field),
                StringUtils.titleCase(oldField),
                StringUtils.titleCase(newField));
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
