
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class AccountingLedger {

    public static void main(String[] args) {

        // Create a Scanner to read user input
        Scanner myScanner = new Scanner(System.in);

        // Load transactions from the CSV file
        ArrayList<Transaction> transactions = getTransactions();

        // Display the main menu
        mainMenu(myScanner, transactions);

        // Display a goodbye message when the user exits
        System.out.println("Thanks for using Accounting Ledger!");
    }

    // Display the main menu
    private static void mainMenu(Scanner myScanner,
                                 ArrayList<Transaction> transactions) {

        // Store the menu options
        String menuPrompt = """
                Welcome to Accounting Ledger!
                D) Add Deposit
                P) Make Payment
                L) Ledger
                X) Exit
                Enter your choice:
                """;

        // Control when the user wants to exit the menu
        boolean done = false;

        do {
            // Display the main menu
            System.out.println(menuPrompt);

            // Read the user's choice from the keyboard
            String userInput = myScanner.nextLine().trim().toUpperCase();

            // Handle the user's menu choice
            switch (userInput) {
                case "D" -> addDeposit(myScanner, transactions);
                case "P" -> addPayment(myScanner, transactions);
                case "L" -> ledgerMenu(myScanner, transactions);
                case "X" -> done = true;
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!done);
    }

    // Display the ledger menu
    private static void ledgerMenu(Scanner myScanner,
                                   ArrayList<Transaction> transactions) {

        // Store the ledger menu options
        String menuPrompt = """
                Ledger Menu
                A) All Transactions
                D) Deposits
                P) Payments
                R) Reports
                H) Home
                Enter your choice:
                """;

        // Control when the user returns to the home menu
        boolean done = false;

        do {
            // Display the ledger menu
            System.out.println(menuPrompt);

            // Read the user's choice
            String userInput = myScanner.nextLine().trim().toUpperCase();

            // Handle the ledger menu choices
            switch (userInput) {
                case "A" -> System.out.println("All Transactions selected");
                case "D" -> System.out.println("Deposits selected");
                case "P" -> System.out.println("Payments selected");
                case "R" -> System.out.println("Reports selected");
                case "H" -> done = true;
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!done);
    }


    // Add a new deposit
    private static void addDeposit(Scanner myScanner,
                                   ArrayList<Transaction> transactions) {

        // Temporary message to test the method
        System.out.println("Add Deposit Menu");


        // Get the current date
        LocalDate date = LocalDate.now();

        // Get the current time
        LocalTime time = LocalTime.now();

        // Display the date and time for testing
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);


        // Ask the user for a deposit description
        System.out.print("Enter deposit description: ");

        // Read the description from the keyboard
        String description = myScanner.nextLine();

        // Display the description for testing
        System.out.println("Description: " + description);



    }


    // Add a new payment
    private static void addPayment(Scanner myScanner,
                                   ArrayList<Transaction> transactions) {

        // Display the payment menu
        System.out.println("Add Payment Menu");

        // Get the current date and time
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        // Display the date and time for testing
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);

        // Ask the user for a payment description
        System.out.print("Enter payment description: ");
        String description = myScanner.nextLine();

        // Display the description for testing
        System.out.println("Description: " + description);

        // Ask the user for the vendor
        System.out.print("Enter vendor: ");
        String vendor = myScanner.nextLine();

        // Display the vendor for testing
        System.out.println("Vendor: " + vendor);

        // Ask the user for the payment amount
        System.out.print("Enter payment amount: ");
        String amountText = myScanner.nextLine();

        // Convert the String into a double
        double amount = Double.parseDouble(amountText);

        // Display the amount for testing
        System.out.println("Amount: " + amount);

        // Convert the amount to a negative value for a payment
        amount = -Math.abs(amount);

        // Create a new Transaction object
        Transaction transaction = new Transaction(
                date, time, description, vendor, amount
        );

        // Add the transaction to the ArrayList
        transactions.add(transaction);

        // Display a confirmation message
        System.out.println("Payment added: " + transaction.getAmount());

        // Save the transaction to the CSV file
        saveTransaction(transaction);
    }


    // Save a transaction to the CSV file
    private static void saveTransaction(Transaction transaction) {

        // Open the CSV file in append mode
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/main/resources/transactions.csv", true))) {

            // Convert the transaction into a CSV line
            String line = transaction.getDate() + "|" +
                    transaction.getTime() + "|" +
                    transaction.getDescription() + "|" +
                    transaction.getVendor() + "|" +
                    transaction.getAmount();

            // Write the transaction to the file
            writer.write(line);

            // Move to the next line
            writer.newLine();

            // Display a confirmation message
            System.out.println("Transaction saved to CSV!");

        } catch (IOException e) {
            // Display an error if the transaction cannot be saved
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }


    // Read transactions from the CSV file
    public static ArrayList<Transaction> getTransactions() {

        // Create an empty list to store transactions
        ArrayList<Transaction> transactions = new ArrayList<>();

        // Open the file using BufferedReader
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/resources/transactions.csv"))) {

            // Skip the header
            reader.readLine();

            String line;

            // Read each line until the end of the file
            while ((line = reader.readLine()) != null) {

                // Split the line into five fields
                String[] parts = line.split("\\|");

                // Convert values to the correct data types
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                // Create a Transaction object
                Transaction transaction = new Transaction(
                        date, time, description, vendor, amount
                );

                // Add the object to the ArrayList
                transactions.add(transaction);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Return the list of transactions
        return transactions;
    }
}
