package com.techlab.inicio.product;

import com.techlab.inicio.utils.ConsoleUtils;
import com.techlab.inicio.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductManager {
    private static final String EXIT = "salir";
    private static String ACCESORY= "accesorio";
    private static String DRINK = "bebida";
    private static String ELECTRONIC = "electronico";
    private static String PACKAGED_PRODUCT = "producto empaquetado";
    private final String[] stringKeyCategory;

    private final ArrayList<Product> products = new ArrayList<>();
    private final Scanner scanner;

    private final Map<String, ProductFactory> categoriesFactoryMap = new HashMap<>();
    private final Map<String, String> categoriesNamesMap = new HashMap<>();
    private final Map<String, Product> productsNameMap = new HashMap<>();
    private final Map<Integer, Product> productsIdMap = new HashMap<>();

    public ProductManager(Scanner scanner) {
        this.scanner = scanner;

        stringKeyCategory = new String[]{
                ACCESORY, DRINK, ELECTRONIC, PACKAGED_PRODUCT
        };

        createCategoriesFactoryMap();
        createCategoriesNames();
    }

    /** categoriesFactoryMap
     * A partir de categoriesNamesMap obtenemos las claves.
     */
    private void createCategoriesFactoryMap(){
        categoriesFactoryMap.put(ACCESORY, Accessory::new);
        categoriesFactoryMap.put(DRINK, Drink::new);
        categoriesFactoryMap.put(ELECTRONIC, Electronic::new);
        categoriesFactoryMap.put(PACKAGED_PRODUCT, PackagedProduct::new);
    }

    /** categoriesNamesMap
     * La clave y contenido son idénticos por simplicidad. De esta forma, obtengo
     * la categoría ya sea que el usuario la seleccione ingresando un número o por
     * su nombre.
     */
    private void createCategoriesNames(){
        for(int i = 0; i < stringKeyCategory.length; i++){
            categoriesNamesMap.put(stringKeyCategory[i], stringKeyCategory[i]);
            categoriesNamesMap.put(String.valueOf(i + 1), stringKeyCategory[i]);
        }
    }

    //1)
    public void addProduct(){
        System.out.println("Ingrese el nuevo Producto. ");
        String name = ConsoleUtils.scanName(scanner);
        if (productsNameMap.get(name) != null){
            System.out.println("Ya existe un producto con el mismo nombre");
            return;
        }
        String brand = ConsoleUtils.scanBrand(scanner);
        double basePrice = ConsoleUtils.scanBasePrice(scanner);
        int stock = ConsoleUtils.scanStock(scanner);
        String categoryName = scanCategoryName();
        ProductFactory productFactory = categoriesFactoryMap.get(categoryName);

        if (productFactory == null){
            System.out.println("Error al crear producto.");
            return;
        }
        Product newProduct = productFactory.create(name, brand, stock, basePrice, scanner);
        if (ConsoleUtils.confirm(scanner, "Desea definir los campos específicos del producto?")){
            newProduct.setSpecificFields();
        }
        products.add(newProduct);
        addProductMap(newProduct);
        System.out.println("Producto agregado exitosamente.");
    }

    //2)
    public void listProducts() {
        if (products.isEmpty()){
            System.out.println("Todavia no se han creado Productos");
            return;
        }
        for (Product product: products)
            product.print();
    }

    //3)
    /**
     * El sistema permitirá buscar un producto por su nombre o ID.Si se encuentra el producto,
     * se mostrará su información completa.
     * Opcionalmente, se podrá actualizar alguno de sus datos (precio o stock),
     * validando que los valores sean coherentes (por ejemplo, que el stock no sea negativo).
     */
    public void searchUpdateProduct() {
        if (products.isEmpty()){
            System.out.println("Todavia no se han creado Productos");
            return;
        }

        Product searchedProduct = searchProduct();
        if (searchedProduct == null) //si es null, se ha ingresado salir
            return;

        searchedProduct.print();
        updateProduct(searchedProduct);
    }

    //4)
    public void deleteProduct(){
        if (products.isEmpty()){
            System.out.println("Todavía no se han cargado productos");
            return;
        }

        Product productToDelete = null;
        String key = null, name = null;
        int id = -1;
        do{
            listProducts();
            key = scanProductKey();
            if (!key.equals(EXIT))
                productToDelete = obtainProduct(key);


            if (productToDelete != null){
                name = productToDelete.getName();
                id = productToDelete.getId();
                removeProduct(StringUtils.normalizeKey(name),id, productToDelete);
            } else{
                System.out.println("Producto no encontrado");
            }

        } while (!key.equals(EXIT) && productToDelete == null);

        if (!key.equals(EXIT))
            System.out.printf("Producto %s(ID: %d) borrado exitosamente\n", name, id);
    }

    private void addProductMap(Product product){
        productsNameMap.put(StringUtils.normalizeKey((product.getName())), product);
        productsIdMap.put(product.getId(), product);
    }

    public void updateProductsNameMap(String oldKey, String newKey){
        Product product = productsNameMap.get(oldKey);
        if (product == null){
            System.out.println("ERROR al buscar producto con oldKey");
            return;
        }
        productsNameMap.remove(oldKey);
        productsNameMap.put(StringUtils.normalizeKey(newKey), product);
    }

    private void removeProduct(String nameKey, int idKey, Product product){
        productsIdMap.remove(idKey);
        productsNameMap.remove(nameKey);
        products.remove(product);
    }

    private void printCategories() {
        for (int i = 0; i < stringKeyCategory.length; i++){
            System.out.printf("%d) %s\n", i + 1 ,StringUtils.titleCase(stringKeyCategory[i]));
        }
        System.out.print("Elija una opción (ingrese número o comando): ");
    }

    private String scanCategoryName(){
        String categoryName = null;

        while (categoryName == null){
            printCategories();
            String option = StringUtils.normalizeKey(scanner.nextLine());
            categoryName = categoriesNamesMap.get(option);

            if (categoryName == null){
                System.out.println("Opción inválida, Ingrese nuevamente");
            }

        }
        return categoryName;
    }

    private void updateProduct(Product product){
        if (!ConsoleUtils.confirm(scanner,"Desea actualizar algun campo?"))
            return;

        String selectedField = selectField(product);
        product.updateField(this, selectedField);
    }

    private String selectField(Product product){
        product.printFullFields();
        System.out.print("Ingrese el campo a seleccionar: ");
        String input = scanner.nextLine();
        String selectedField = product.getField(input);

        while (selectedField == null){
            System.out.println("Error, Campo inexistente");
            System.out.print("Ingrese el campo a seleccionar: ");
            input = scanner.nextLine();
            selectedField = product.getField(input);
        }

        return selectedField;
    }

    public Product searchProduct(){
        String key = scanProductKey();
        if (key.equalsIgnoreCase("salir"))
            return null;
        Product searchedProduct = obtainProduct(key);

        while (searchedProduct == null){
            System.out.println("Producto no encontrado");
            key = scanProductKey();
            if (key.equalsIgnoreCase("salir"))
                return null;
            searchedProduct = obtainProduct(key);
        }

        return searchedProduct;
    }

    // todo: revisar trycatch. la funcion busca por nombre primero, y sino por ID
    private Product obtainProduct(String key) {
        if (key == null || key.isEmpty()){
            System.out.println("Error: No se encontró key");
            return null;
        }
        Product product = null;

        product = findByName(key);
        if (product != null)
            return product;

        product = findById(key);
        return product;
    }

    private Product findById(String key){
        Product product = null;
        try {
            int id = Integer.parseInt(key);
            product = productsIdMap.get(id);
        } catch (NumberFormatException ignored) {
        }
        return product;
    }

    private Product findByName(String key){
        return productsNameMap.get(key);
    }

    private String scanProductKey(){
        listProducts();
        String key;


        do {
            System.out.print("Ingrese el Nombre o ID del producto (o salir para cancelar): ");
            key = scanner.nextLine().trim();
            if (key.isEmpty()) {
                System.out.println("Error, ingrese nuevamente");
            }
        } while (key.isEmpty());


        return StringUtils.normalizeKey(key);
    }


    public ArrayList<Product> getProducts(){
        return products;
    }
}
