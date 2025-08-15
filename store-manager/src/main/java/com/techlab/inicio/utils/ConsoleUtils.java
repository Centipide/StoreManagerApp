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
        return scanString(scanner, "Ingrese el nombre: ");
    }

    public static double scanBasePrice(Scanner scanner){
        return scanDouble(scanner, "Ingrese un precio base: ");
    }

    public static int scanStock(Scanner scanner){
        return scanIntGreaterThan(scanner, 0,"Ingrese cantidad de stock: " ,"Ingrese una cantidad de stock válida: ");
    }

    public static String scanBrand(Scanner scanner) {
        return scanString(scanner, "Ingrese el nombre de la marca: ");
    }

    public static int scanAmountOfDifProducts(Scanner scanner){
        return scanIntGreaterThan(scanner, 1,"Ingrese cantidad productos a agregar al pedido: ", "Ingrese una cantidad de productos valida: ");
    }

    public static int scanUnits(Scanner scanner){
        return scanIntGreaterThan(scanner, 1, "Ingrese cantidad de unidades del producto: ","Ingrese una cantidad de unidades valida: ");
    }

    public static int scanOrderIDToDelete(Scanner scanner){
        return scanIntGreaterThan(scanner, 1, "Ingrese el ID de la orden a eliminar: ", "Ingrese un ID valido: ");
    }

    public static String scanMaterial(Scanner scanner){
        return scanString(scanner, "Ingrese el/los materiales que componen el producto: ");
    }

    public static String scanColour(Scanner scanner){
        return scanString(scanner, "Ingrese el/los colores del producto: ");
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

    public static String scanString(Scanner scanner, String requestMsg){
        String text;
        do {
            System.out.print(requestMsg);
            text = scanner.nextLine().trim();

            String error = validateString(text);
            if (error != null) {
                System.out.println(error);
                text = null;
            }

        } while (text == null);

        return text;
    }

    public static double scanDouble(Scanner scanner, String requestMsg){
        double number = -1;

        do {
            System.out.print(requestMsg);
            try{
                number = scanner.nextDouble();
                scanner.nextLine();

                if (number < 0){
                    System.out.println("Error, Ingrese un número válido.");
                }

            } catch (InputMismatchException e){
                System.out.println("Error, ingrese un número valido");
                scanner.nextLine();
            }

        } while (number < 0);

        return number;
    }

    private static String validateString(String text) {
        if (text.isEmpty()) {
            return "Error, ingrese nuevamente";
        }
        if (text.matches("\\d+")) {
            return "Error, el campo no puede tener solo números";
        }
        return null;
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
