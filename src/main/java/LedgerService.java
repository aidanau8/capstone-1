import java.util.ArrayList;      // 	Stores multiple transactions
import java.io.BufferedReader;   //     Reads text from a file
import java.io.FileReader;       //     Opens a file for reading
import java.io.IOException;      //     Handles file-reading errors
import java.time.LocalDate;      //     Represents a date
import java.time.LocalTime;      //     Represents a time

public class LedgerService {

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
