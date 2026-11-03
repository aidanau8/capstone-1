import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<Transaction> transactions = LedgerService.getTransactions();

        System.out.println("Transactions loaded: " + transactions.size());

    }
}