import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccountingLedger {

    public static void main(String[] args) {

        ArrayList<Transaction> transactions = getTransactions();

        System.out.println("Transactions loaded: " + transactions.size());

        System.out.println("\nWelcome to Accounting Ledger!");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.print("Enter your choice: ");
    }

    public static ArrayList<Transaction> getTransactions() {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/main/resources/transactions.csv"))) {

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction transaction = new Transaction(
                        date, time, description, vendor, amount
                );

                transactions.add(transaction);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return transactions;
    }
}