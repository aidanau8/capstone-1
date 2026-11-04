
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
            String userInput = myScanner.nextLine().toUpperCase();


            // Handle the user's menu choice
            switch (userInput) {
                case "D" -> System.out.println("Add Deposit selected");
                case "P" -> System.out.println("Make Payment selected");
                case "L" -> ledgerMenu(myScanner, transactions);
                case "X" -> done = true;
                default -> System.out.println("Invalid option. Please try again.");
            }


        } while (!done);
    }



    // Display the ledger menu
    private static void ledgerMenu(Scanner myScanner,
                                   ArrayList<Transaction> transactions) {


        // Store the ledger menu options in a text block
        String menuPrompt = """
                Ledger Menu
                A) All Transactions
                D) Deposits
                P) Payments
                R) Reports
                H) Home
                Enter your choice:
                """;

        // Display the ledger menu
        System.out.println(menuPrompt);



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

                    // Add the object to the list
                    transactions.add(transaction);
                }

            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }

            return transactions;
        }
}
