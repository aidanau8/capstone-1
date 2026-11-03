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

            String line;

            while ((line = reader.readLine()) != null) {

                System.out.println(line);

            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return transactions;
    }

}
