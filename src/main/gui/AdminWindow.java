package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import services.Bank;
import models.user.Employee;
import models.user.Client;
import models.account.Transaction;
import services.FileManager;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class AdminWindow extends JFrame {
    private final Bank bank;

    // UI Components
    private JTable employeeTable;
    private JTable clientTable;
    private JTable transactionTable;

    public AdminWindow() {
        this.bank = Bank.getInstance();
        setTitle("Admin Dashboard");
        setSize(800, 600);  // Set an initial size for the window
        setResizable(false);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Add a WindowListener to handle the close button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveData();
                System.exit(0); // Exit the program
            }
        });

        // Initialize UI components
        initializeComponents();
    }

    private void initializeComponents() {
        JButton applyFilterButton, openAuthDialogButton, viewEmployeesButton, createEmployeeButton, viewClientsButton, showTransactionsButton, logoutButton;
        JTextField filterInputField;
        JComboBox<String> filterTypeComboBox;

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2));

        // Button to open authorization dialog
        openAuthDialogButton = getButton("Authorize Employee");
        openAuthDialogButton.addActionListener(e -> openAuthorizationDialog());
        buttonPanel.add(openAuthDialogButton);

        // Button to view all employees
        viewEmployeesButton = getButton("View Employees");
        viewEmployeesButton.addActionListener(e -> displayEmployees());
        buttonPanel.add(viewEmployeesButton);

        // Button to view all clients
        viewClientsButton = getButton("View Clients");
        viewClientsButton.addActionListener(e -> displayClients());
        buttonPanel.add(viewClientsButton);

        // Button to view transactions
        showTransactionsButton = getButton("View All Transactions");
        showTransactionsButton.addActionListener(e -> showTransactions(bank.getTransactions()));
        buttonPanel.add(showTransactionsButton);

        // Button to Create new Employee
        createEmployeeButton = getButton("Create Employee");
        createEmployeeButton.addActionListener(e -> openCreateEmployeeDialog());
        buttonPanel.add(createEmployeeButton);

        // Filtering
        buttonPanel.add(new JLabel("Filter Transactions By:"));

        // Dropdown for filter type
        filterTypeComboBox = new JComboBox<>(new String[]{"Filter by Date", "Filter by Client ID"});
        buttonPanel.add(filterTypeComboBox);

        // Input field for the selected filter
        filterInputField = getTextField();
        buttonPanel.add(filterInputField);

        // Apply Filter Button
        applyFilterButton = new JButton("Apply Filter");
        applyFilterButton.addActionListener(e -> {
            String selectedFilter = (String) filterTypeComboBox.getSelectedItem();
            String input = filterInputField.getText().trim();

            if (selectedFilter.equals("Filter by Date")) {
                filterTransactionsByDate(input);
            } else if (selectedFilter.equals("Filter by Client ID")) {
                filterTransactionsByClientId(input);
            }
        });
        buttonPanel.add(applyFilterButton);

        // Add the button panel to the window
        add(buttonPanel, BorderLayout.NORTH);

        // Initialize tables for data display
        employeeTable = new JTable();
        clientTable = new JTable();
        transactionTable = new JTable();

        // Wrap each table in a JScrollPane to make them scrollable
        JScrollPane employeeScrollPane = new JScrollPane(employeeTable);
        JScrollPane clientScrollPane = new JScrollPane(clientTable);
        JScrollPane transactionScrollPane = new JScrollPane(transactionTable);

        // Add the tables as a JPanel in the center section
        JPanel tablePanel = new JPanel(new CardLayout());
        tablePanel.add(employeeScrollPane, "Employees");
        tablePanel.add(clientScrollPane, "Clients");
        tablePanel.add(transactionScrollPane, "Transactions");
        add(tablePanel, BorderLayout.CENTER);

        // Panel for Logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 12));
        logoutButton.setPreferredSize(new Dimension(100, 30));  // Smaller button size
        logoutButton.addActionListener(e -> logout());
        logoutPanel.add(logoutButton);

        // Add the logout panel to the SOUTH (bottom)
        add(logoutPanel, BorderLayout.SOUTH);
    }

    private void filterTransactionsByDate(String date) {

        if (date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a date in dd-MM-yyyy format.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate filterDate;
        try {
            filterDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            showTransactions(bank.getTransactionsByDate(filterDate));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use dd-MM-yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTransactionsByClientId(String clientId) {
        if (clientId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Client ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            showTransactions(bank.getTransactionsByClient(bank.getClientById(clientId)));
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JButton getButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    private JTextField getTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));
        return textField;
    }

    private void saveData() {
        try {
            String clientsFilePath = "src/main/data/clients.json";
            String employeesFilePath = "src/main/data/employees.json";

            FileManager.saveToJson(clientsFilePath, bank.getClients());
            FileManager.saveToJson(employeesFilePath, bank.getEmployees());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to display all employees
    private void displayEmployees() {
        List<Employee> employees = bank.getEmployees();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Employee ID");
        model.addColumn("Name");
        model.addColumn("Position");
        model.addColumn("Active");

        for (Employee employee : employees) {
            model.addRow(new Object[]{employee.userId, employee.getFirstName() + " " + employee.getLastName(), employee.getPosition(), employee.isActive()});
        }

        employeeTable.setModel(model);
        updateTableView("Employees");
    }

    // Method to display all clients
    private void displayClients() {
        List<Client> clients = bank.getClients();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Client ID");
        model.addColumn("Name");
        model.addColumn("Phone Number");
        model.addColumn("Number of Accounts");
        model.addColumn("Loyalty Points");
        model.addColumn("No. Transactions Made");

        for (Client client : clients) {
            model.addRow(new Object[]{client.userId, client.getFirstName() + " " + client.getLastName(), client.getPhoneNumber(), client.getAccounts().size(), client.getLoyaltyPoints().getPoints(), client.getTransactionHistory().size()});
        }

        clientTable.setModel(model);
        updateTableView("Clients");
    }

    // Method to show all transactions
    private void showTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Transaction ID");
        model.addColumn("Amount");
        model.addColumn("Type");
        model.addColumn("Date");
        model.addColumn("Client ID");

        for (Transaction transaction : transactions) {
            model.addRow(new Object[]{
                    transaction.getTransactionId(),
                    transaction.getAmount(),
                    transaction.getType(),
                    transaction.getDate(),
                    transaction.clientId,
            });
        }

        transactionTable.setModel(model);
        updateTableView("Transactions");
    }

    // Method to switch between table views (employees, clients, transactions)
    private void updateTableView(String view) {
        CardLayout cardLayout = (CardLayout) ((JPanel) getContentPane().getComponent(1)).getLayout();
        cardLayout.show((JPanel) getContentPane().getComponent(1), view);
    }

    // Method to open the employee authorization dialog
    private void openAuthorizationDialog() {

        // Fields for username and password
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] fields = {"Username: ", usernameField,
                            "Password: ", passwordField
        };

        int response;
        do{
            response = JOptionPane.showConfirmDialog(this, fields, "Authorize Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(response == JOptionPane.OK_OPTION){
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                try {
                    bank.authorizeEmployee(bank.getEmployeeByUsername(username));
                    JOptionPane.showMessageDialog(this, "Employee authorized successfully.");
                    break;
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while (response == JOptionPane.OK_OPTION);
    }

    // Create Employee Button
    private void openCreateEmployeeDialog() {

        // Fields for username and password
        JTextField firstNameField = getTextField();
        JTextField lastNameField = getTextField();
        JTextField addressField = getTextField();
        JTextField positionField = getTextField();
        JTextField usernameField = getTextField();
        JPasswordField passwordField = new JPasswordField(20);
        JTextField graduatedCollegeField = getTextField();
        JTextField yearofGraduationField = getTextField();
        JTextField totalGradeField = getTextField();

        Object[] fields = {
                "First Name", firstNameField,
                "Last Name", lastNameField,
                "Address", addressField,
                "Position", positionField,
                "Username", usernameField,
                "Password", passwordField,
                "Graduated College", graduatedCollegeField,
                "Year of Graduation", yearofGraduationField,
                "Total Grade", totalGradeField
        };

        int response;
        do {
            response = JOptionPane.showConfirmDialog(this, fields, "Create Employee", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (response == JOptionPane.OK_OPTION) {
                try {
                    String username = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String address = addressField.getText();
                    String position = positionField.getText();
                    String graduatedCollege = graduatedCollegeField.getText();
                    String totalGrade = totalGradeField.getText();
                    if (username.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || address.isEmpty() || position.isEmpty() || graduatedCollege.isEmpty() || totalGrade.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    int yearOfGraduation = Integer.parseInt(yearofGraduationField.getText());

                    bank.addEmployee(new Employee(firstName, lastName, address, position, username, password, graduatedCollege, yearOfGraduation, totalGrade));
                    JOptionPane.showMessageDialog(this, "Employee created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    break;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid graduation year", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } while (response == JOptionPane.OK_OPTION);

        this.setVisible(true);
    }

    // Logout Button
    private void logout() {
        saveData();
        new LoginWindow().setVisible(true);
        this.dispose();
    }
}