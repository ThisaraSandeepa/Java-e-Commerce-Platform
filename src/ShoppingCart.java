import javax.swing.table.DefaultTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

    private List<Product> systemProductList;

    // Constructors
    ShoppingCart() {
        this.systemProductList = new ArrayList<>();
    }

    // Add a product to the shopping cart.
    public void addProduct(Product product) {
        systemProductList.add(product);
    }

    // Get a DefaultTableModel representing the products in the shopping cart.
    public DefaultTableModel getTableModel() {

        // Create a DefaultTableModel to hold data for a JTable.
        DefaultTableModel tableModel = new DefaultTableModel();

        // Add columns to the table model.
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Price");

        // Iterate through products and add each product's data to the table model.
        for (Product product : systemProductList) {
            Object[] rowData = {product.getProductId(), product.getProductName(), product.getPrice()};
            tableModel.addRow(rowData);
        }

        return tableModel;
    }

    // Calculate total details, including original total, discount amount, and final total.
    public TotalDetails calculateTotal() {

        double originalTotal = systemProductList.stream().mapToDouble(Product::getPrice).sum();
        double discountAmount = 0.0;

        // Apply discount for each product type with at least 3 items.
        long electronicsCount = systemProductList.stream().filter(p -> p instanceof Electronics).count();
        long clothingCount = systemProductList.stream().filter(p -> p instanceof Clothing).count();

        if (electronicsCount >= 3) {
            discountAmount += (originalTotal * 0.20);
        }

        if (clothingCount >= 3) {
            discountAmount += (originalTotal * 0.20);
        }

        double finalTotal = originalTotal - discountAmount;
        return new TotalDetails(originalTotal, discountAmount, finalTotal);
    }

    // Inner class representing total details, including original total, discount amount, and final total.
    public class TotalDetails implements Serializable {
        private final double originalTotal;
        private final double discountAmount;
        private final double finalTotal;

        // Constructor to initialize total details.
        public TotalDetails(double originalTotal, double discountAmount, double finalTotal) {
            this.originalTotal = originalTotal;
            this.discountAmount = discountAmount;
            this.finalTotal = finalTotal;
        }

        // Getters for total details.
        public double getOriginalTotal() {
            return originalTotal;
        }

        public double getDiscountAmount() {
            return discountAmount;
        }

        public double getFinalTotal() {
            return finalTotal;
        }
    }
}
