import java.io.*;

public class Clothing extends Product implements Serializable {

    // Initialize the variables
    private String size;
    private String colour;

    // Constructors
    public Clothing(String productId, String productName, int numOfItems, double price, String size, String colour) {
        super(productId, productName, numOfItems, price);
        this.size = size;
        this.colour = colour;
    }

    // Getters
    public String getSize() {
        return size;
    }

    public String getColour() {
        return colour;
    }

    // Setters
    public void setSize(String size) {
        this.size = size;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    // Implementation of the abstract method of the Product Class
    @Override
    public String displayInformation() {

        return  "Clothing" +
                "\nProduct ID: " + getProductId() +
                "\nProduct Name: " + getProductName() +
                "\nSize: " + size +
                "\nColour: " + colour +
                "\nAvailable Items: " + getNumOfItems() +
                "\nPrice: $" + getPrice();
    }
}

