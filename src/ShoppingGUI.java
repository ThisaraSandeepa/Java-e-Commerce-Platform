import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class ShoppingGUI extends JFrame {

    private ArrayList<Product> systemProductList;
    private DefaultTableModel productTableModel;
    private JPanel selectedProductDetailsPanel;
    private JLabel selectedProductDetailsLabel;
    private JTable productTable;

    ShoppingCart shoppingCart = new ShoppingCart();

    public ShoppingGUI() {

        // Create an instance of ShoppingCart
        shoppingCart = new ShoppingCart();

        // Create the main panel
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        add(mainPanel);

        // Create the upper panel
        JPanel upperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(upperPanel);

        // Create the prompt panel
        JPanel promptPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        upperPanel.add(promptPanel, BorderLayout.CENTER);

        // The select product category text
        JLabel productCategoryText = new JLabel("Select Product Category");
        promptPanel.add(productCategoryText);

        // Create the dropdown menu
        String[] dropdownOfItemsList = {"All", "Electronics", "Clothing"};
        JComboBox<String> dropdownOfItems = new JComboBox<>(dropdownOfItemsList);
        promptPanel.add(dropdownOfItems);

        // Create Shopping Cart button
        JButton shoppingCartButton = new JButton("Shopping Cart");
        upperPanel.add(shoppingCartButton, BorderLayout.EAST);

        // Add ActionListener for the "Shopping Cart" button
        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the shopping cart frame
                openShoppingCartFrame();
            }
        });

        // Create the mid-panel
        JPanel midPanel = new JPanel(new BorderLayout());
        mainPanel.add(midPanel);

        // Create the product table
        productTable = new JTable();
        productTableModel = new DefaultTableModel();
        productTableModel.addColumn("Product ID");
        productTableModel.addColumn("Name");
        productTableModel.addColumn("Category");
        productTableModel.addColumn("Price");
        productTableModel.addColumn("Info");
        productTable.setModel(productTableModel);

        // Add the product table to a scroll pane for better visibility if many rows
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        midPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Create the selected product details panel
        selectedProductDetailsPanel = new JPanel(new BorderLayout());
        selectedProductDetailsLabel = new JLabel("Selected Product Details");
        selectedProductDetailsPanel.add(selectedProductDetailsLabel, BorderLayout.CENTER);
        midPanel.add(selectedProductDetailsPanel, BorderLayout.SOUTH);

        // Create the bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(bottomPanel);

        // Create "Add to ShoppingCart" button
        JButton addToCartButton = new JButton("Add to ShoppingCart");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(addToCartButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListener for the "Add to ShoppingCart" button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();

                if (selectedRow != -1) {
                    String productId = productTable.getValueAt(selectedRow, 0).toString();
                    String productName = productTable.getValueAt(selectedRow, 1).toString();
                    double productPrice = Double.parseDouble(productTable.getValueAt(selectedRow, 3).toString());

                    Product selectedProduct;
                    String productCategory = productTable.getValueAt(selectedRow, 2).toString();
                    if (productCategory.equals("Electronics")) {
                        selectedProduct = new Electronics(productId, productName, 1, productPrice, "", 0);
                    } else {
                        selectedProduct = new Clothing(productId, productName, 1, productPrice, "", "");
                    }

                    shoppingCart.addProduct(selectedProduct);
                    updateProductTable();
                    JOptionPane.showMessageDialog(ShoppingGUI.this, "Product added to ShoppingCart!");

                } else {
                    JOptionPane.showMessageDialog(ShoppingGUI.this, "Please select a product to add to the ShoppingCart!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set frame properties
        setTitle("Westminster Shopping Centre");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Load the data from the textFile
        loadDataFromFile();

        // Update the table
        updateProductTable();
    }

    // Load the data from the textFile
    private void loadDataFromFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("Output.txt"))) {
            systemProductList = (ArrayList<Product>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading data from file.", "Error", JOptionPane.ERROR_MESSAGE);

            // Initialize the list to an empty ArrayList if an error occurs
            systemProductList = new ArrayList<>();
        }
    }

    // Updates the values to the table by taking from the arrayList
    private void updateProductTable() {
        // Clear the existing data in the table
        productTableModel.setRowCount(0);

        // Add each product to the table
        for (Product product : systemProductList) {
            // Take the value of Electronic or Clothes to a variable
            String productCategory = (product instanceof Electronics) ? "Electronics" : "Clothes";

            // Take the value of Electronic or Clothes to a variable
            String productInfo = (product instanceof Electronics) ? "Electronics" : "Clothes";

            Object[] rowData = {product.getProductId(), product.getProductName(),
                    productCategory, product.getPrice(), productInfo};
            productTableModel.addRow(rowData);
        }
    }

    // Open the shopping cart frame
    private void openShoppingCartFrame() {
        JFrame shoppingCartFrame = new JFrame("Shopping Cart");
        shoppingCartFrame.setSize(800, 500);

        // Create a table to display the shopping cart contents
        JTable cartTable = new JTable(shoppingCart.getTableModel());

        // Add the table to a scroll pane for better visibility if many rows
        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        shoppingCartFrame.add(tableScrollPane);

        // Create a label for displaying the total price
        ShoppingCart.TotalDetails totalDetails = shoppingCart.calculateTotal();
        JLabel totalLabel = new JLabel("Original Total: $" + totalDetails.getOriginalTotal()
                + ", Discount: $" + totalDetails.getDiscountAmount()
                + ", Final Total: $" + totalDetails.getFinalTotal());
        shoppingCartFrame.add(totalLabel, BorderLayout.SOUTH);

        // Set the new frame properties
        shoppingCartFrame.setLocationRelativeTo(null); // Center the frame on the screen
        shoppingCartFrame.setVisible(true);
    }

    public static void callingTheGUI(){
        SwingUtilities.invokeLater(() -> {
            ShoppingGUI frame = new ShoppingGUI();
            frame.setVisible(true);
        });
    }
}
