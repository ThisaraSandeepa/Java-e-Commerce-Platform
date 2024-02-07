import java.io.*;

public class Electronics extends Product implements Serializable {

    // Initialize the variables
    private String brandOfElecItem;
    private int warrantyPeriod;

    // Constructors
    public Electronics(
            String productId, String productName, int numOfItems,
            double price, String brandOfElecItem, int warrantyPeriod) {

        super(productId, productName, numOfItems, price);
        this.brandOfElecItem = brandOfElecItem;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters
    public String getBrandOfElecItem() {
        return brandOfElecItem;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    // Setters
    public void setBrandOfElecItem(String BrandOfElecItem) {
        this.brandOfElecItem = BrandOfElecItem;
    }

    // Implementation of the abstract method of the Product Class
    @Override
    public String displayInformation() {

        return  "Electronic" +
                "\nProduct ID: " + getProductId() +
                "\nProduct Name: " + getProductName() +
                "\nBrand: " + brandOfElecItem +
                "\nWarranty Period: " + warrantyPeriod + " months" +
                "\nAvailable Items: " + getNumOfItems() +
                "\nPrice: $" + getPrice();

    }
}
