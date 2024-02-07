import javax.swing.*;
import java.io.*;
import java.util.*;

// Implement the interface
interface ShoppingManager {
    void addSystemProduct();
    void removeSystemProduct();
    void printAllTheItems();
    void saveToFile();
}
public class WestminsterShoppingManager implements ShoppingManager,Serializable {

    // Create the productList
    private ArrayList<Product> systemProductList = new ArrayList<>();

    // Define the scanner
    static Scanner input = new Scanner(System.in);

    // Add product to the system
    public void addSystemProduct() {

        // Check the systemProductList does not contain more than 50 items
        if (systemProductList.size() >= 50) {
            System.out.println("The maximum limit of products reached!");

        } else {

            // Asking the user the type of the adding item
            System.out.print("\nInsert the type of the item : \n" +
                    "\n1. Electronic Item" +
                    "\n2. Clothing Item\n");

            System.out.print("\nEnter your choice: ");
            String insertedItem = input.next();

            // Redirect according to the user Input
            switch (insertedItem) {
                case "1":
                    try {
                        System.out.print("\nEnter product ID: ");
                        String elecProductId = input.next();

                        // Check if the product ID already exists
                        if (isProductIdExists(elecProductId)) {
                            System.out.println("Product with the same ID already exists. Please enter a different product ID.");
                            addSystemProduct();
                            return;
                        }

                        System.out.print("Enter product name: ");
                        String eleProductName = input.next();

                        System.out.print("Enter number of items: ");
                        int eleNumOfItems = input.nextInt();

                        System.out.print("Enter price: ");
                        double elePrice = input.nextDouble();

                        System.out.print("Enter brand of electronic item: ");
                        String eleBrandOfElecItem = input.next();

                        System.out.print("Enter warranty period in months: ");
                        int eleWarrantyPeriod = input.nextInt();

                        // Create the object of the Electronic Class
                        Electronics e = new Electronics(elecProductId, eleProductName, eleNumOfItems, elePrice, eleBrandOfElecItem, eleWarrantyPeriod);

                        // Add the item to the list
                        systemProductList.add(e);
                        System.out.println(
                                "\nSuccessfully inserted the item!" +
                                        "\nRedirecting to the main menu...");

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
                        addSystemProduct();
                    }
                    break;

                case "2":
                    try {
                        System.out.print("\nEnter product ID: ");
                        String cloProductId = input.next();

                        // Check if the product ID already exists
                        if (isProductIdExists(cloProductId)) {
                            System.out.println("Product with the same ID already exists. Please enter a different product ID.");
                            addSystemProduct();
                            return;
                        }

                        // Product Name Input
                        System.out.print("Enter product name: ");
                        String cloProductName = input.next();

                        // Quantity of Products Input
                        System.out.print("Enter number of items: ");
                        int cloNumOfItems = input.nextInt();

                        // Product Price Input
                        System.out.print("Enter price: ");
                        double cloPrice = input.nextDouble();

                        // Checking if the user enters the correct size
                        String[] validSizes = {"S", "M", "L", "XL"};
                        String cloSize;

                        do {
                            System.out.print("Enter size (S, M, L, XL): ");
                            cloSize = input.next().toUpperCase();

                            if (!Arrays.asList(validSizes).contains(cloSize)) {
                                System.out.println("Invalid input! Please enter a valid size from S, M, L and XL!");
                            }

                        } while (!Arrays.asList(validSizes).contains(cloSize));

                        // Colour Input
                        System.out.print("Enter colour: ");
                        String cloColour = input.next();

                        // Create the object of the Electronic Class
                        Clothing c = new Clothing(
                                cloProductId, cloProductName, cloNumOfItems,
                                cloPrice, cloSize, cloColour);

                        // Add the item to the list
                        systemProductList.add(c);
                        System.out.println(
                                "\nSuccessfully inserted the item!" +
                                        "\nRedirecting to the main menu...");

                    } catch (InputMismatchException e) {
                        System.out.println("Invalid Input!");
                        input.nextLine();
                        addSystemProduct();
                    }
                    break;

                default:
                    System.out.println("Invalid Input!");
                    addSystemProduct();
            }
        }
    }

    // Method to check if a product ID already exists in the list
    private boolean isProductIdExists(String productId) {
        for (Product product : systemProductList) {
            if (product.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    // Remove product from the system
    public void removeSystemProduct() {

        // Initialize the counter variable for the number the objects of the ArrayList
        int productCount = 1;

        // Display all the products that available
        System.out.println("\nThese are the products that are available in the system");

        if (systemProductList.size() == 0) {
            System.out.println("Empty Product List!");

        } else {

            // Printing the available list
            printAllTheItems();

            // Asking the user that what he needs to remove
            System.out.print("\nEnter the product ID of the product that you need to remove : ");

            // Checking all the exceptions that might occur
            try {
                // Removing the element
                String removeInput = input.next();

                // Check if the product id and user inputs matches
                if (systemProductList.removeIf(product -> product.getProductId().equals(removeInput))) {
                    System.out.println("Successfully Removed the Product!");

                } else {
                    System.out.println("Invalid Product ID!");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input!");
                input.nextLine();
                removeSystemProduct();

            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid Product Number!");
                removeSystemProduct();
            }
        }

        System.out.println("\nRedirecting to the main menu...");
    }

    // Print all the items in the system
    public void printAllTheItems() {

        // Sort the systemProductList based on Product ID
        systemProductList.sort(Comparator.comparing(Product::getProductId));

        // Initialize the counter variable for the number the objects of the ArrayList
        int productCount = 1;

        // Print the items if there or output Empty List
        if (systemProductList.isEmpty()) {
            System.out.println("Empty List!");

        } else {
            for (Product product : systemProductList) {
                System.out.println("--------------------------");
                System.out.println("Product #" + productCount + ":");
                System.out.println(product.displayInformation());
                productCount++;
            }
        }
    }

    // Save all the items into a file
    public void saveToFile() {

        // References for the objectOutputStream: https://www.programiz.com/java-programming/objectinputstream
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("Output.txt"))) {
            objectOutputStream.writeObject(systemProductList);
            System.out.println("Updates saved to file.\n\n");

        } catch (IOException e) {
            System.out.println("Error saving updates to file.\n\n");
        }
    }

    // Load items from the file
    public void loadFromFile() {

        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Output.txt"))) {
            systemProductList = (ArrayList<Product>) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error reading products from file.\n\n");
        }
    }

    // inputData method
    public static void inputData(WestminsterShoppingManager obj) {

        // Initialize the variables
        String userInput;

        // Prompt to the console
        System.out.println("\n----------Westminster Shopping Management---------");
        System.out.println("\n 1. Add a new product" +
                "\n 2. Delete a product" +
                "\n 3. Print the list of the products" +
                "\n 4. Save in a file " +
                "\n 5. Exit");

        // Take the user input
        System.out.print("\nEnter your choice : ");
        userInput = input.next();

        switch (userInput) {
            case "1":
                obj.addSystemProduct();
                break;
            case "2":
                obj.removeSystemProduct();
                break;
            case "3":
                obj.printAllTheItems();
                System.out.println("\nRedirecting to the main menu...");
                break;
            case "4":
                obj.saveToFile();
                break;
            case "5":
                System.out.println("Exiting the Shopping Manager....");
                Main.mainUserInput();
            default:
                System.out.println("Invalid Input!\n");
        }
    }
}
