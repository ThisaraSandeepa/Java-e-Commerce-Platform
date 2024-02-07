import java.io.Serializable;

abstract class Product implements Serializable {

    // Initialize the variables
    private String productId;
    private String productName;
    private int numOfItems;
    private double price;

    // Constructors
    public Product(String productId, String productName, int numOfItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.numOfItems = numOfItems;
        this.price = price;
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public double getPrice() {
        return price;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract methods

    // Creating the abstract method to use in the Electronics and Clothing classes
    public abstract String displayInformation();

}
