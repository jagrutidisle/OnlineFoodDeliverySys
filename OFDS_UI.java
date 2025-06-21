package dbms_miniproj;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.sql.*;

public class dbms_miniproj {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/OFDS?useSSL=false";
    static final String USER = "root";
    static final String PASS = "root";
    private static Connection conn;

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            SwingUtilities.invokeLater(dbms_miniproj::createAndShowGUI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Online Food Delivery System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout(10, 10));

        // Title label with updated font size
        JLabel titleLabel = new JLabel(" ONLINE FOOD DELIVERY SYSTEM ", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Algerian", Font.BOLD, 30));  // Increased font size
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons with GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 2, 5, 5));  // Smaller button spacing

        // Buttons
        JButton displayButton = new JButton("Display Data");
        JButton insertButton = new JButton("Insert Data");
        JButton update1Button = new JButton("Update Customer Details");
        JButton update2Button = new JButton("Update Menu Item Availability");
        JButton update3Button = new JButton("Update Payment Status");
        JButton deleteButton = new JButton("Delete a customer");

        // Setting a smaller font size for the buttons
        Font buttonFont = new Font("PT Sans", Font.PLAIN, 22);
        displayButton.setFont(buttonFont);
        insertButton.setFont(buttonFont);
        update1Button.setFont(buttonFont);
        update2Button.setFont(buttonFont);
        update3Button.setFont(buttonFont);
        deleteButton.setFont(buttonFont);

        // Adding action listeners to buttons
        displayButton.addActionListener(e -> displayData());
        insertButton.addActionListener(e -> insertDataMenu());
        update1Button.addActionListener(e -> updateCustomerDetails());
        update2Button.addActionListener(e -> updateMenuAvailability());
        update3Button.addActionListener(e -> updatePaymentStatus());
        deleteButton.addActionListener(e -> deleteCustomer());

        // Adding buttons to the panel
        buttonPanel.add(displayButton);
        buttonPanel.add(insertButton);
        buttonPanel.add(update1Button);
        buttonPanel.add(update2Button);
        buttonPanel.add(update3Button);
        buttonPanel.add(deleteButton);

        // Add the button panel to the center of the frame
        frame.add(buttonPanel, BorderLayout.CENTER);

        // Panel for the exit button (centered at the bottom)
        JPanel exitPanel = new JPanel();
        exitPanel.setLayout(new FlowLayout(FlowLayout.CENTER));  // Center the exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(buttonFont);
        exitButton.addActionListener(e -> System.exit(0));
        exitPanel.add(exitButton);

        // Add the exit panel to the bottom of the frame
        frame.add(exitPanel, BorderLayout.SOUTH);

        // Center the frame on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    private static void displayData() {
        JFrame frame = new JFrame("Display Data");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JButton displayCustomerButton = new JButton("Display All Customers Data");
        JButton displayCustomerPButton = new JButton("Display Prime Customer Data");
        JButton displayCustomerRButton = new JButton("Display Regular Customer Data");
        JButton displayMenuButton = new JButton("Display Menu Data");
        JButton displayOrderItemsButton = new JButton("Display Order Items Data");
        JButton displayPaymentButton = new JButton("Display Payment Data");

        displayCustomerButton.addActionListener(e -> displayCustomerData());
        displayCustomerPButton.addActionListener(e -> displayPrimeCustomer());
        displayCustomerRButton.addActionListener(e -> displayRegularCust());
        displayMenuButton.addActionListener(e -> displayMenuData());
        displayOrderItemsButton.addActionListener(e -> displayOrderItemsData());
        displayPaymentButton.addActionListener(e -> displayPaymentData());

        frame.add(displayCustomerButton);
        frame.add(displayCustomerPButton);
        frame.add(displayCustomerRButton);
        frame.add(displayMenuButton);
        frame.add(displayOrderItemsButton);
        frame.add(displayPaymentButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void insertDataMenu() {
        JFrame frame = new JFrame("Insert Data");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JButton insertCustomerButton = new JButton("Insert New Customer");
        JButton insertMenuButton = new JButton("Insert New Menu Item");
        JButton insertOrderItemButton = new JButton("Insert New Order Item");
        JButton insertPaymentButton = new JButton("Insert New Payment");

        insertCustomerButton.addActionListener(e -> insertCustomerData());
        insertMenuButton.addActionListener(e -> insertMenuData());
        insertOrderItemButton.addActionListener(e -> insertOrderItemsData());
        insertPaymentButton.addActionListener(e -> insertPaymentData());

        frame.add(insertCustomerButton);
        frame.add(insertMenuButton);
        frame.add(insertOrderItemButton);
        frame.add(insertPaymentButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void displayCustomerData() {
        JFrame frame = new JFrame("Customer Data");
        frame.setSize(600, 400);
        // Create JTextArea with word wrap enabled
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // Make the text area non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Use monospaced font for better alignment
        // Add the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        String sql = "SELECT * FROM Customer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {            
            // StringBuilder to build the tabular format
            StringBuilder sb = new StringBuilder();           
            // Header row
            sb.append(String.format("%-10s %-30s %-15s %-15s %-20s\n", "ID", "Name", "Mobile", "DOB", "City"));
            sb.append("-------------------------------------------------------------------------------------\n");
            // Iterate through the result set and format each row
            while (rs.next()) {
                sb.append(String.format("%-10d %-30s %-15s %-15s %-20s\n",
                        rs.getInt("c_no"),
                        rs.getString("c_name"),
                        rs.getString("c_mob"),
                        rs.getDate("c_dob"),
                        rs.getString("c_city")));
            }
            // Set the text of the JTextArea to display the formatted data
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
    
    
    private static void displayPrimeCustomer() {
        JFrame frame = new JFrame("Prime Customer Data");
        frame.setSize(600, 400); 
        // Create JTextArea with word wrap enabled
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));       
        // Add the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        String sql = "SELECT * FROM PrimeCustomer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            // StringBuilder to build the tabular format
            StringBuilder sb = new StringBuilder();
            
            // Header row
            sb.append(String.format("%-15s %-15s %-15s %-15s\n", "Customer ID", "Start Date", "End Date", "Amount Paid"));
            sb.append("-------------------------------------------------------------------------------\n");
            
            // Iterate through the result set and format each row
            while (rs.next()) {
                sb.append(String.format("%-15d %-15s %-15s %-15.2f\n",
                        rs.getInt("c_no"),
                        rs.getDate("dom_start"),
                        rs.getDate("dom_end"),
                        rs.getDouble("amount_paid")));
            }
            
            // Set the text of the JTextArea to display the formatted data
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    
    private static void displayRegularCust() {
        JFrame frame = new JFrame("Regular Customer Data");
        frame.setSize(600, 400);
        
        // Create JTextArea with word wrap enabled
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // Make the text area non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Use monospaced font for better alignment
        
        // Add the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);

        String sql = "SELECT * FROM RegularCustomer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             
            // StringBuilder to build the tabular format
            StringBuilder sb = new StringBuilder();
            
            // Header row
            sb.append(String.format("%-15s %-15s\n", "Customer ID", "Points"));
            sb.append("-------------------------------\n");
            
            // Iterate through the result set and format each row
            while (rs.next()) {
                sb.append(String.format("%-15d %-15d\n",
                        rs.getInt("c_no"),
                        rs.getInt("points")));
            }
            
            // Set the text of the JTextArea to display the formatted data
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }


    private static void displayMenuData() {
        JFrame frame = new JFrame("Menu Data");
        frame.setSize(600, 400);
        // Create JTextArea with word wrap enabled
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // Make the text area non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Use monospaced font for better alignment
        // Add the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        String sql = "SELECT * FROM Menu";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {         
            // StringBuilder to build the tabular format
            StringBuilder sb = new StringBuilder();          
            // Header row
            sb.append(String.format("%-10s %-30s %-10s %-15s %-10s\n", "Item ID", "Name", "Price", "Type", "Available"));
            sb.append("-----------------------------------------------------------------------------------\n");
            // Iterate through the result set and format each row
            while (rs.next()) {
                sb.append(String.format("%-10d %-30s %-10.2f %-15s %-10b\n",
                        rs.getInt("m_id"),
                        rs.getString("m_name"),
                        rs.getDouble("m_price"),
                        rs.getString("m_type"),
                        rs.getBoolean("m_available")));
            }
            // Set the text of the JTextArea to display the formatted data
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }


    private static void displayOrderItemsData() {
        JFrame frame = new JFrame("Order Items Data");
        frame.setSize(600, 400);
        // Create JTextArea with word wrap enabled
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // Make the text area non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Use monospaced font for better alignment
        // Add the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        String sql = "SELECT * FROM OrderItems";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // StringBuilder to build the tabular format
            StringBuilder sb = new StringBuilder();
            // Header row
            sb.append(String.format("%-10s %-15s %-10s %-12s %-10s\n", "Order ID", "Customer ID", "Menu ID", "Quantity", "Price"));
            sb.append("---------------------------------------------------------------\n");
            // Iterate through the result set and format each row
            while (rs.next()) {
                sb.append(String.format("%-10d %-15d %-10d %-12d %-10.2f\n",
                        rs.getInt("o_id"),
                        rs.getInt("c_no"),
                        rs.getInt("m_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("o_price")));
            }
            // Set the text of the JTextArea to display the formatted data
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }


    private static void displayPaymentData() {
        JFrame frame = new JFrame("Payment Data");
        frame.setSize(600, 400);
        // Create JTextArea with word wrap enabled
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);  // Make the text area non-editable
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));  // Use monospaced font for better alignment
        // Add the JTextArea inside a JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        frame.add(scrollPane);
        String sql = "SELECT * FROM Payment";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // StringBuilder to build the tabular format
            StringBuilder sb = new StringBuilder();           
            // Header row
            sb.append(String.format("%-10s %-10s %-15s %-15s %-12s\n", "Payment ID", "Order ID", "Date", "Method", "Status"));
            sb.append("---------------------------------------------------------------\n");
            // Iterate through the result set and format each row
            while (rs.next()) {
                sb.append(String.format("%-10d %-10d %-15s %-15s %-12s\n",
                        rs.getInt("p_id"),
                        rs.getInt("o_id"),
                        rs.getDate("p_date"),
                        rs.getString("p_method"),
                        rs.getString("p_status")));
            }
            // Set the text of the JTextArea to display the formatted data
            textArea.setText(sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);
    }
    
    
    private static void insertCustomerData() {
        JFrame frame = new JFrame("Insert Customer");
        frame.setSize(400, 500);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Fields for Customer Information
        JTextField customerIDField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField mobileField = new JTextField(15);
        JTextField dobField = new JTextField(15);
        JTextField cityField = new JTextField(15);
        JComboBox<String> customerTypeComboBox = new JComboBox<>(new String[]{"Regular", "Prime"});
        
        // Fields for PrimeCustomer details
        JTextField domStartField = new JTextField(15);
        JTextField domEndField = new JTextField(15);
        JTextField amountPaidField = new JTextField(15);
        
        // Field for RegularCustomer details
        JTextField pointsField = new JTextField(15);

        // Adding components with labels
        gbc.gridx = 0;  gbc.gridy = 0;
        mainPanel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(customerIDField, gbc);

        gbc.gridx = 0;  gbc.gridy = 1;
        mainPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        gbc.gridx = 0;  gbc.gridy = 2;
        mainPanel.add(new JLabel("Mobile:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(mobileField, gbc);

        gbc.gridx = 0;  gbc.gridy = 3;
        mainPanel.add(new JLabel("DOB (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(dobField, gbc);

        gbc.gridx = 0;  gbc.gridy = 4;
        mainPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(cityField, gbc);

        gbc.gridx = 0;  gbc.gridy = 5;
        mainPanel.add(new JLabel("Customer Type:"), gbc);
        gbc.gridx = 1;
        mainPanel.add(customerTypeComboBox, gbc);

        // Adding PrimeCustomer fields
        gbc.gridx = 0;  gbc.gridy = 6;
        mainPanel.add(new JLabel("DOM Start (Prime only):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(domStartField, gbc);

        gbc.gridx = 0;  gbc.gridy = 7;
        mainPanel.add(new JLabel("DOM End (Prime only):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(domEndField, gbc);

        gbc.gridx = 0;  gbc.gridy = 8;
        mainPanel.add(new JLabel("Amount Paid (Prime only):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(amountPaidField, gbc);

        // Adding RegularCustomer field
        gbc.gridx = 0;  gbc.gridy = 9;
        mainPanel.add(new JLabel("Points (Regular only):"), gbc);
        gbc.gridx = 1;
        mainPanel.add(pointsField, gbc);

        // Submit button
        JButton submitButton = new JButton("Submit");
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Common Customer data
                    int customerID = Integer.parseInt(customerIDField.getText());
                    String name = nameField.getText();
                    long mobile = Long.parseLong(mobileField.getText());
                    Date dob = Date.valueOf(dobField.getText());
                    String city = cityField.getText();
                    // Insert into Customer table
                    String sql = "INSERT INTO Customer (c_no, c_name, c_mob, c_dob, c_city) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, customerID);
                        stmt.setString(2, name);
                        stmt.setLong(3, mobile);
                        stmt.setDate(4, dob);
                        stmt.setString(5, city);
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "Customer added successfully.");
                    }
                    // Insert additional data based on Customer Type
                    String customerType = (String) customerTypeComboBox.getSelectedItem();
                    if ("Prime".equals(customerType)) {
                        Date domStart = Date.valueOf(domStartField.getText());
                        Date domEnd = Date.valueOf(domEndField.getText());
                        double amountPaid = Double.parseDouble(amountPaidField.getText());
                        sql = "INSERT INTO PrimeCustomer (c_no, dom_start, dom_end, amount_paid) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setInt(1, customerID);
                            stmt.setDate(2, domStart);
                            stmt.setDate(3, domEnd);
                            stmt.setDouble(4, amountPaid);
                            stmt.executeUpdate();
//                            JOptionPane.showMessageDialog(frame, "Prime Customer added successfully.");
                        }
                    } else if ("Regular".equals(customerType)) {
                        int points = Integer.parseInt(pointsField.getText());
                        sql = "INSERT INTO RegularCustomer (c_no, points) VALUES (?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            stmt.setInt(1, customerID);
                            stmt.setInt(2, points);
                            stmt.executeUpdate();
//                            JOptionPane.showMessageDialog(frame, "Regular Customer added successfully.");
                        }
                    }
                } catch (SQLException | NumberFormatException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    private static void insertMenuData() {
        JFrame frame = new JFrame("Insert Menu Item");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField menuIDField = new JTextField();
        JTextField itemNameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField typeField = new JTextField();
        JCheckBox availableCheckbox = new JCheckBox();

        frame.add(new JLabel("Menu ID:"));
        frame.add(menuIDField);
        frame.add(new JLabel("Item Name:"));
        frame.add(itemNameField);
        frame.add(new JLabel("Price:"));
        frame.add(priceField);
        frame.add(new JLabel("Type (Veg/Non-veg):"));
        frame.add(typeField);
        frame.add(new JLabel("Available:"));
        frame.add(availableCheckbox);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Menu (m_id, m_name, m_price, m_type, m_available) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setInt(1, Integer.parseInt(menuIDField.getText()));
                stmt.setString(2, itemNameField.getText());
                stmt.setDouble(3, Double.parseDouble(priceField.getText()));
                stmt.setString(4, typeField.getText());
                stmt.setBoolean(5, availableCheckbox.isSelected());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Menu item added successfully!");
                frame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error inserting data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(submitButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void insertOrderItemsData() {
        JFrame frame = new JFrame("Insert Order Item");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField orderIDField = new JTextField();
        JTextField customerIDField = new JTextField();
        JTextField menuIDField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField priceField = new JTextField();

        frame.add(new JLabel("Order ID:"));
        frame.add(orderIDField);
        frame.add(new JLabel("Customer ID:"));
        frame.add(customerIDField);
        frame.add(new JLabel("Menu ID:"));
        frame.add(menuIDField);
        frame.add(new JLabel("Quantity:"));
        frame.add(quantityField);
//        frame.add(new JLabel("Price:"));
//        frame.add(priceField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO OrderItems (o_id, c_no, m_id, quantity) VALUES (?, ?, ?, ?)")) {
                stmt.setInt(1, Integer.parseInt(orderIDField.getText()));
                stmt.setInt(2, Integer.parseInt(customerIDField.getText()));
                stmt.setInt(3, Integer.parseInt(menuIDField.getText()));
                stmt.setInt(4, Integer.parseInt(quantityField.getText()));
//                stmt.setDouble(5, Double.parseDouble(priceField.getText()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Order item added successfully!");
                frame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error inserting data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(submitButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void insertPaymentData() {
        JFrame frame = new JFrame("Insert Payment");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 2, 5, 5));

        JTextField paymentIDField = new JTextField();
        JTextField orderIDField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField methodField = new JTextField();
        JTextField statusField = new JTextField();

        frame.add(new JLabel("Payment ID:"));
        frame.add(paymentIDField);
        frame.add(new JLabel("Order ID:"));
        frame.add(orderIDField);
        frame.add(new JLabel("Date (YYYY-MM-DD):"));
        frame.add(dateField);
        frame.add(new JLabel("Method:"));
        frame.add(methodField);
        frame.add(new JLabel("Status:"));
        frame.add(statusField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO Payment (p_id, o_id, p_date, p_method, p_status) VALUES (?, ?, ?, ?, ?)")) {
                stmt.setInt(1, Integer.parseInt(paymentIDField.getText()));
                stmt.setInt(2, Integer.parseInt(orderIDField.getText()));
                stmt.setDate(3, Date.valueOf(dateField.getText()));
                stmt.setString(4, methodField.getText());
                stmt.setString(5, statusField.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Payment added successfully!");
                frame.dispose();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error inserting data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(submitButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void updateCustomerDetails() {
        JFrame frame = new JFrame("Update Customer Details");
        frame.setSize(400, 500);
        frame.setLayout(new GridLayout(11, 2, 5, 5));

        JTextField customerIDField = new JTextField();
        JTextField customerNameField = new JTextField();
        JTextField mobileField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField cityField = new JTextField();
        JComboBox<String> customerTypeCombo = new JComboBox<>(new String[]{"Prime", "Regular"});

        JTextField domStartField = new JTextField();
        JTextField domEndField = new JTextField();
        JTextField amountPaidField = new JTextField();
        JTextField pointsField = new JTextField();

        // Add fields to frame
        frame.add(new JLabel("Customer Number:"));
        frame.add(customerIDField);
        frame.add(new JLabel("Customer Name:"));
        frame.add(customerNameField);
        frame.add(new JLabel("Mobile Number:"));
        frame.add(mobileField);
        frame.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        frame.add(dobField);
        frame.add(new JLabel("City:"));
        frame.add(cityField);
        frame.add(new JLabel("Customer Type (Prime/Regular):"));
        frame.add(customerTypeCombo);

        // Additional fields for Prime customers
        frame.add(new JLabel("Membership Start Date (Prime only, YYYY-MM-DD):"));
        frame.add(domStartField);
        frame.add(new JLabel("Membership End Date (Prime only, YYYY-MM-DD):"));
        frame.add(domEndField);
        frame.add(new JLabel("Amount Paid (Prime only):"));
        frame.add(amountPaidField);

        // Field for Regular customers
        frame.add(new JLabel("Points (Regular only):"));
        frame.add(pointsField);

        JButton submitButton = new JButton("Update");
        submitButton.addActionListener(e -> {
            int c_no = Integer.parseInt(customerIDField.getText());
            String c_name = customerNameField.getText();
            long c_mob = Long.parseLong(mobileField.getText());
            String c_dob = dobField.getText();
            String c_city = cityField.getText();
            String customerType = (String) customerTypeCombo.getSelectedItem();

            try {
                // Update Customer table
                String sql = "UPDATE Customer SET c_name = ?, c_mob = ?, c_dob = ?, c_city = ? WHERE c_no = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, c_name);
                    pstmt.setLong(2, c_mob);
                    pstmt.setDate(3, Date.valueOf(c_dob));
                    pstmt.setString(4, c_city);
                    pstmt.setInt(5, c_no);
                    pstmt.executeUpdate();
                }

                if ("Prime".equalsIgnoreCase(customerType)) {
                    String dom_start = domStartField.getText();
                    String dom_end = domEndField.getText();
                    double amount_paid = Double.parseDouble(amountPaidField.getText());

                    // Remove RegularCustomer entry if it exists
                    sql = "DELETE FROM RegularCustomer WHERE c_no = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, c_no);
                        pstmt.executeUpdate();
                    }

                    // Insert PrimeCustomer details
                    sql = "INSERT INTO PrimeCustomer (c_no, dom_start, dom_end, amount_paid) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, c_no);
                        pstmt.setDate(2, Date.valueOf(dom_start));
                        pstmt.setDate(3, Date.valueOf(dom_end));
                        pstmt.setDouble(4, amount_paid);
                        pstmt.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(frame, "Updated as Prime Customer.");
                } else if ("Regular".equalsIgnoreCase(customerType)) {
                    int points = Integer.parseInt(pointsField.getText());

                    // Remove PrimeCustomer entry if it exists
                    sql = "DELETE FROM PrimeCustomer WHERE c_no = ?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, c_no);
                        pstmt.executeUpdate();
                    }

                    // Insert RegularCustomer details
                    sql = "INSERT INTO RegularCustomer (c_no, points) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, c_no);
                        pstmt.setInt(2, points);
                        pstmt.executeUpdate();
                    }
                    JOptionPane.showMessageDialog(frame, "Updated as Regular Customer.");
                }
                frame.dispose();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error updating customer details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(submitButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void updateMenuAvailability() {
        JFrame frame = new JFrame("Update Menu Item Availability");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 2, 5, 5));

        JTextField menuIdField = new JTextField();
        JCheckBox availabilityCheckbox = new JCheckBox("Available");

        // Add components to frame
        frame.add(new JLabel("Enter Menu ID:"));
        frame.add(menuIdField);
        frame.add(new JLabel("Set Availability:"));
        frame.add(availabilityCheckbox);

        JButton updateButton = new JButton("Update Availability");
        updateButton.addActionListener(e -> {
            int menuId;
            boolean isAvailable = availabilityCheckbox.isSelected();

            try {
                menuId = Integer.parseInt(menuIdField.getText());

                String sql = "UPDATE Menu SET m_available = ? WHERE m_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setBoolean(1, isAvailable);
                    pstmt.setInt(2, menuId);
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Menu availability updated successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Menu item not found.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating menu availability.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                frame.dispose(); // Close the frame after updating

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Menu ID.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });

        frame.add(new JLabel()); // Empty label for spacing
        frame.add(updateButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    
    private static void updatePaymentStatus() {
        // Frame for updating payment status
        JFrame frame = new JFrame("Update Payment Status");
        frame.setSize(400, 150);
        frame.setLayout(new GridLayout(3, 1, 5, 5));

        JLabel label = new JLabel("Enter Payment ID to update status to 'Paid':");
        JTextField paymentIdField = new JTextField();
        JButton updateButton = new JButton("Update Status");

        // Action listener for the button to execute update
        updateButton.addActionListener(e -> {
            try {
                int paymentId = Integer.parseInt(paymentIdField.getText().trim());

                String sql = "UPDATE Payment SET p_status = 'Paid' WHERE p_id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, paymentId);
                    int rowsUpdated = pstmt.executeUpdate();

                    // Show message based on rows affected
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(frame, "Payment status updated to 'Paid' successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No record found with the provided Payment ID.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error updating payment status.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Payment ID.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add components to the frame
        frame.add(label);
        frame.add(paymentIdField);
        frame.add(updateButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    
    private static void deleteCustomer() {
        // Frame for deleting customer
        JFrame frame = new JFrame("Delete Customer");
        frame.setSize(400, 150);
        frame.setLayout(new GridLayout(3, 1, 5, 5));

        JLabel label = new JLabel("Enter Customer Number to delete:");
        JTextField customerNumberField = new JTextField();
        JButton deleteButton = new JButton("Delete Customer");

        // Action listener for the delete button
        deleteButton.addActionListener(e -> {
            try {
                int customerNumber = Integer.parseInt(customerNumberField.getText().trim());

                String sql = "DELETE FROM Customer WHERE c_no = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setInt(1, customerNumber);
                    int rowsAffected = pstmt.executeUpdate();

                    // Show message based on rows affected
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "Customer and their related records deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(frame, "No customer found with the given number.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error deleting customer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid Customer Number.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Add components to the frame
        frame.add(label);
        frame.add(customerNumberField);
        frame.add(deleteButton);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
