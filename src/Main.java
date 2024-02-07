import java.util.Scanner;

public class Main {

    // Creates the object
    static WestminsterShoppingManager obj = new WestminsterShoppingManager();

    // Define the main prompt in a method to do recursion
    public static void mainUserInput() {

        // Run the loadFromFile method to take the previous products from the textfile
        obj.loadFromFile();

        // Prompt of the main
        System.out.println("" +
                "\n----------Welcome---------\n" +
                "\nAre you a Customer or a Shopping Manager?\n" +
                "\n1. Customer" +
                "\n2. Shopping Manager" +
                "\n3. Exit");

        // Define the Scanner
        Scanner input = new Scanner(System.in);

        // Asking for the user inputs
        System.out.print("\nEnter your choice: ");
        String choice = input.next();

        switch(choice) {
            case "1":
                System.out.println("Launching the GUI.....");
                ShoppingGUI.callingTheGUI();
                break;

            case "2":

                // Calling the inputData method
                while(true) {
                    WestminsterShoppingManager.inputData(obj);
                }

            case "3":
                System.out.println("Exiting the Program....");
                System.exit(0);

            default:
                System.out.println("Invalid Input!");
                mainUserInput();
        }
    }

    // Main method
    public static void main(String[] args) {

        // Calling the main method of the program
        mainUserInput();

    }
}
