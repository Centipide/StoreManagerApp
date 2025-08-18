package com.techlab.inicio.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
        return scanString(
                scanner,
                "Ingrese el nombre: "
        );
    }

    public static double scanBasePrice(Scanner scanner){
        return scanDoubleGreaterThan(
                scanner,
                0 ,
                "Ingrese un precio base: ",
                "Ingrese un precio base valido: "
        );
    }

    public static int scanStock(Scanner scanner){
        return scanIntGreaterThan(
                scanner,
                0,
                "Ingrese cantidad de stock: " ,
                "Ingrese una cantidad de stock válida: "
        );
    }

    public static String scanBrand(Scanner scanner) {
        return scanString(
                scanner,
                "Ingrese el nombre de la marca: "
        );
    }

    public static int scanAmountOfDifProducts(Scanner scanner){
        return scanIntGreaterThan(
                scanner,
                1,
                "Ingrese cantidad productos a agregar al pedido: ",
                "Ingrese una cantidad de productos valida (mayor a cero): "
        );
    }

    public static int scanUnits(Scanner scanner){
        return scanIntGreaterThan(
                scanner,
                1,
                "Ingrese cantidad de unidades del producto: ",
                "Ingrese una cantidad de unidades valida (mayor a cero): "
        );
    }

    public static int scanOrderIDToDelete(Scanner scanner){
        return scanIntGreaterThan(
                scanner,
                1,
                "Ingrese el ID de la orden a eliminar: ",
                "Ingrese un ID valido (mayor a 0): "
        );
    }

    public static String scanMaterial(Scanner scanner){
        return scanString(
                scanner,
                "Ingrese el/los materiales que componen el producto: "
        );
    }

    public static String scanColour(Scanner scanner){
        return scanString(
                scanner,
                "Ingrese el/los colores del producto: "
        );
    }

    public static double scanVolume(Scanner scanner){
        return scanDoubleGreaterThan(
                scanner,
                0 ,
                "Ingrese el volumen el litros: ",
                "Ingrese un volumen en litros valido: "
        );
    }

    public static String scanType(Scanner scanner){
        return scanString(
                scanner,
                "Ingrese el tipo de bebida (soda, jugo, agua, alcohol, etc.): "
        );
    }

    public static String scanRecTemperature(Scanner scanner){
        return scanString(
                scanner,
                "Ingrese temperatura recomendada (frio, temperatura ambiente, etc.): "
        );
    }

    public static double scanWeight(Scanner scanner){
        return scanDoubleGreaterThan(
                scanner,
                1,
                "Ingrese el peso en gramos: ",
                "Ingrese un peso en gramos valido :"
        );
    }

    public static LocalDate scanExpirationDate(Scanner scanner){
        return scanDate(
                scanner,
                LocalDate.now(),
                "Ingrese la fecha de vencimiento (ej: 16/08/2025): ",
                "Ingrese una fecha de vencimiento valida"
        );
    }

    public static String scanIngredients(Scanner scanner){
        return scanString(
                scanner,
                "Ingrese los ingredientes del producto: "
        );
    }

    public static String scanModel(Scanner scanner){
        return scanString(
                scanner,
                "Ingrese el modelo del producto: "
        );
    }

    public static int scanWarrantyMonths(Scanner scanner){
        return scanIntGreaterThan(scanner,
                0,
                "Ingrese la cantidad de meses de garantía: ",
                "Ingrese una cantidad de meses valida (0 o más): "
        );
    }

    public static double scanPowerConsumption(Scanner scanner){
        return scanDoubleGreaterThan(
                scanner,
                0,
                "Ingrese el consumo eléctrio en Watts: ",
                "Ingrese un valor de consumo válido en Watts (0 o más): "
        );
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

    public static double scanDoubleGreaterThan(Scanner scanner, int minLimit, String requestMsg, String errMsg){
        int number = minLimit - 1;

        while (!(number >= minLimit)){
            System.out.print(requestMsg);

            try{
                number = scanner.nextInt();
                scanner.nextLine();

                if (!(number >= minLimit)){
                    System.out.println(errMsg);
                }
            } catch (InputMismatchException e){
                System.out.println("Error, ingrese un número válido");
                scanner.nextLine();
            }
        }

        return number;
    }

    public static LocalDate scanDate(Scanner scanner, LocalDate minDate, String requestMsg, String errMsg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;

        while (date == null) {
            System.out.print(requestMsg);
            String input = scanner.nextLine().trim();

            try {
                date = LocalDate.parse(input, formatter);

                if (date.isBefore(minDate)) {
                    System.out.println(errMsg + " (La fecha debe ser posterior o igual a " + minDate.format(formatter) + ")");
                    date = null;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use el formato dd/MM/yyyy.");
            }
        }

        return date;
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
