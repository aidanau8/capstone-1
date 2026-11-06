
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

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
                case "A" -> displayAllTransactions(transactions);
                case "D" -> displayDeposits(transactions);
                case "P" -> displayPayments(transactions);
                case "R" -> reportsMenu(myScanner, transactions);
                case "H" -> done = true;
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!done);
    }

    // Display the reports menu
    private static void reportsMenu(Scanner myScanner,
                                    ArrayList<Transaction> transactions) {

        // Store the reports menu options
        String menuPrompt = """
                Reports Menu
                1) Month To Date
                2) Previous Month
                3) Year To Date
                4) Previous Year
                5) Search by Vendor
                0) Back
                Enter your choice:
                """;

        // Control when the user returns to the ledger menu
        boolean done = false;

        do {
            // Display the reports menu
            System.out.println(menuPrompt);

            // Read the user's choice
            String userInput = myScanner.nextLine().trim();

            // Handle the reports menu choices
            switch (userInput) {
                case "1" -> monthToDateReport(transactions);
                case "2" -> previousMonthReport(transactions);
                case "3" -> yearToDateReport(transactions);
                case "4" -> previousYearReport(transactions);
                case "5" -> searchByVendor(myScanner, transactions);
                case "0" -> done = true;
                default -> System.out.println("Invalid option. Please try again.");
            }

        } while (!done);
    }


    // Display transactions from the current month
    private static void monthToDateReport(ArrayList<Transaction> transactions) {

        // Get today's date
        LocalDate today = LocalDate.now();

        // Get the first day of the current month
        LocalDate startDate = today.withDayOfMonth(1);

        // Display the month to date report
        displayDateRangeReport(
                transactions, startDate, today, "Month To Date Report");
    }

    // Display transactions from the previous month
    private static void previousMonthReport(ArrayList<Transaction> transactions) {

        // Get the first day of the previous month
        LocalDate startDate = LocalDate.now()
                .minusMonths(1).withDayOfMonth(1);

        // Get the last day of the previous month
        LocalDate endDate = LocalDate.now()
                .withDayOfMonth(1).minusDays(1);

        // Display the previous month report
        displayDateRangeReport(
                transactions, startDate, endDate, "Previous Month Report");
    }

    // Display transactions from the current year
    private static void yearToDateReport(ArrayList<Transaction> transactions) {

        // Get today's date
        LocalDate today = LocalDate.now();

        // Get the first day of the current year
        LocalDate startDate = today.withDayOfYear(1);

        // Display the year to date report
        displayDateRangeReport(
                transactions, startDate, today, "Year To Date Report");
    }

    // Display transactions from the previous year
    private static void previousYearReport(ArrayList<Transaction> transactions) {

        // Get the first day of the previous year
        LocalDate startDate = LocalDate.now()
                .minusYears(1).withDayOfYear(1);

        // Get the last day of the previous year
        LocalDate endDate = LocalDate.now()
                .withDayOfYear(1).minusDays(1);

        // Display the previous year report
        displayDateRangeReport(
                transactions, startDate, endDate, "Previous Year Report");
    }

    // Display transactions within a selected date range
    private static void displayDateRangeReport(
            ArrayList<Transaction> transactions,
            LocalDate startDate,
            LocalDate endDate,
            String reportName) {

        // Create a copy to keep the original list unchanged
        ArrayList<Transaction> sortedTransactions = new ArrayList<>(transactions);

        // Sort transactions by date and time, newest first
        Collections.sort(sortedTransactions,
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());

        // Display the report heading
        System.out.println(reportName);
        System.out.println("From: " + startDate);
        System.out.println("To: " + endDate);

        // Track whether matching transactions were found
        boolean found = false;

        // Loop through the sorted transactions
        for (Transaction transaction : sortedTransactions) {

            // Get the transaction date
            LocalDate transactionDate = transaction.getDate();

            // Check if the date is within the selected range
            if (!transactionDate.isBefore(startDate) &&
                    !transactionDate.isAfter(endDate)) {

                // Display transaction details
                printTransaction(transaction);
                found = true;
            }
        }

        // Display a message when no transactions are found
        if (!found) {
            System.out.println("No transactions found.");
        }
    }

    // Search for transactions by vendor
    private static void searchByVendor(Scanner myScanner,
                                       ArrayList<Transaction> transactions) {

        // Ask the user for a vendor name
        System.out.print("Enter vendor name: ");
        String vendorName = myScanner.nextLine().trim();

        // Check if the vendor name is empty
        if (vendorName.isEmpty()) {
            System.out.println("Please enter a vendor name.");
            return;
        }

        // Create a copy to keep the original list unchanged
        ArrayList<Transaction> sortedTransactions = new ArrayList<>(transactions);

        // Sort transactions by date and time, newest first
        Collections.sort(sortedTransactions,
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());

        // Display the report heading
        System.out.println("Search by Vendor: " + vendorName);

        // Track whether matching transactions were found
        boolean found = false;

        // Loop through the sorted transactions
        for (Transaction transaction : sortedTransactions) {

            // Check if the vendor name contains the search text
            if (transaction.getVendor().toLowerCase()
                    .contains(vendorName.toLowerCase())) {

                // Display matching transaction details
                printTransaction(transaction);
                found = true;
            }
        }

        // Display a message when no transactions are found
        if (!found) {
            System.out.println("No transactions found.");
        }
    }

    // Display all transactions
    private static void displayAllTransactions(ArrayList<Transaction> transactions) {

        // Create a copy to keep the original list unchanged
        ArrayList<Transaction> sortedTransactions = new ArrayList<>(transactions);

        // Sort transactions by date and time, newest first
        Collections.sort(sortedTransactions,
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());

        // Display a heading
        System.out.println("All Transactions:");

        // Loop through the sorted transactions
        for (Transaction transaction : sortedTransactions) {

            // Display transaction details
            printTransaction(transaction);
        }
    }

    // Display a single transaction
    private static void printTransaction(Transaction transaction) {

        // Display transaction details
        System.out.println(
                transaction.getDate() + " | " +
                        transaction.getTime() + " | " +
                        transaction.getDescription() + " | " +
                        transaction.getVendor() + " | " +
                        transaction.getAmount()
        );
    }

    // Display deposit transactions
    private static void displayDeposits(ArrayList<Transaction> transactions) {

        // Create a copy to keep the original list unchanged
        ArrayList<Transaction> sortedTransactions = new ArrayList<>(transactions);

        // Sort transactions by date and time, newest first
        Collections.sort(sortedTransactions,
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());

        // Display a heading
        System.out.println("Deposits:");

        // Loop through the sorted transactions
        for (Transaction transaction : sortedTransactions) {

            // Display only positive transactions
            if (transaction.getAmount() > 0) {

                // Display deposit details
                printTransaction(transaction);
            }
        }
    }

    // Display payment transactions
    private static void displayPayments(ArrayList<Transaction> transactions) {

        // Create a copy to keep the original list unchanged
        ArrayList<Transaction> sortedTransactions = new ArrayList<>(transactions);

        // Sort transactions by date and time, newest first
        Collections.sort(sortedTransactions,
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed());

        // Display a heading
        System.out.println("Payments:");

        // Loop through the sorted transactions
        for (Transaction transaction : sortedTransactions) {

            // Display only negative transactions
            if (transaction.getAmount() < 0) {

                // Display payment details
                printTransaction(transaction);
            }
        }
    }

    // Add a new deposit
    private static void addDeposit(Scanner myScanner,
                                   ArrayList<Transaction> transactions) {

        // Display the deposit menu
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

        // Ask the user for the deposit vendor
        System.out.print("Enter deposit vendor: ");

        // Read the vendor from the keyboard
        String vendor = myScanner.nextLine();

        // Display the vendor for testing
        System.out.println("Vendor: " + vendor);

        // Ask the user for the deposit amount
        System.out.print("Enter deposit amount: ");

        // Read a valid deposit amount
        double amount = readAmount(myScanner);

        // Ensure the deposit amount is positive
        amount = Math.abs(amount);

        // Display the amount for testing
        System.out.println("Amount: " + amount);

        // Create a new Transaction object
        Transaction transaction = new Transaction(
                date, time, description, vendor, amount
        );

        // Add the transaction to the ArrayList
        transactions.add(transaction);

        // Display a confirmation message
        System.out.println("Deposit added: " + transaction.getAmount());

        // Save the deposit transaction to the CSV file
        saveTransaction(transaction);
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

        // Read a valid payment amount
        double amount = readAmount(myScanner);

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


    // Read and validate a transaction amount
    private static double readAmount(Scanner myScanner) {

        while (true) {

            // Read the amount from the keyboard
            String amountText = myScanner.nextLine().trim();

            try {
                // Convert the String into a double
                double amount = Double.parseDouble(amountText);

                // Check that the amount is valid and greater than zero
                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }

                System.out.print("Please enter an amount greater than zero: ");

            } catch (NumberFormatException e) {

                // Handle invalid number input
                System.out.print("Invalid amount. Please enter a number: ");
            }
        }
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
