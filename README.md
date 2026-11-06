# Accounting Ledger Application

## Project Description

Accounting Ledger is a Java command-line application developed as part of the Year Up United Application Development program.

The application allows users to manage financial transactions by recording deposits, making payments, viewing transaction history, and generating reports.

All transactions are stored in a CSV file, allowing users to access their financial records when the application is restarted.

## Technologies Used

- Java 17
- Maven
- IntelliJ IDEA
- Java Collections (`ArrayList`)
- Java Date and Time API (`LocalDate`, `LocalTime`)
- File I/O (`BufferedReader`, `BufferedWriter`)
- Git and GitHub

## Application Features

### Main Menu

- **D) Add Deposit** — Record a new deposit as a positive amount.
- **P) Make Payment** — Record a new payment as a negative amount.
- **L) Ledger** — Open the ledger menu.
- **X) Exit** — Exit the application.

### Ledger Menu

- **A) All Transactions** — Display all transactions, newest first.
- **D) Deposits** — Display positive transactions.
- **P) Payments** — Display negative transactions.
- **R) Reports** — Open the reports menu.
- **H) Home** — Return to the main menu.

### Reports Menu

1. **Month To Date** — Display transactions from the beginning of the current month through today.
2. **Previous Month** — Display transactions from the previous calendar month.
3. **Year To Date** — Display transactions from the beginning of the current year through today.
4. **Previous Year** — Display transactions from the previous calendar year.
5. **Search by Vendor** — Search for transactions by vendor name without case sensitivity.
6. **Back** — Return to the ledger menu.

Transactions are sorted by date and time, newest first.

## Project Structure

```text
accountingledger/
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   ├── AccountingLedger.java
        │   └── Transaction.java
        └── resources/
            └── transactions.csv
```

### Java Classes

**AccountingLedger.java**

Contains the main application logic, including menus, transaction processing, CSV file operations, and reporting methods.

**Transaction.java**

Represents an individual financial transaction with the following fields:

- Date
- Time
- Description
- Vendor
- Amount

## How to Run the Application

1. Clone or download the GitHub repository: https://github.com/aidanau8/capstone-1
2. Open the `accountingledger` Maven project in IntelliJ IDEA.
3. Configure Java 17 as the project SDK.
4. Ensure the `transactions.csv` file exists in `src/main/resources/`.
5. Open `AccountingLedger.java`.
6. Run the `main()` method.
7. Follow the instructions displayed in the console.

Run the application from the `accountingledger` project directory so the CSV file can be located correctly.

## Example Transaction

```text
2026-11-05 | 20:07:23.756319 | Coffee | Starbacks | -9.0
```

Each transaction contains a date, time, description, vendor, and amount.

- Positive amounts represent deposits.
- Negative amounts represent payments.

The application uses the `|` character to separate transaction fields in the CSV file.

## Development Process

The project was developed step by step using Git and GitHub for version control.

The main development milestones included:

1. Creating the Maven project and `Transaction` class.
2. Implementing CSV file reading.
3. Building the main menu and ledger menu.
4. Adding deposit and payment functionality.
5. Implementing transaction filtering.
6. Sorting transactions by date and time.
7. Creating the reports menu.
8. Implementing four date-based reports.
9. Adding case-insensitive vendor search.
10. Refactoring transaction output using the reusable `printTransaction()` method.

Git commit history documents the development progress.

## Future Improvements

Potential improvements include:

- Additional input validation and error handling.
- Financial summaries and balance calculations.
- Improved currency and date formatting.
- Additional report filtering options.

## Author

Aidana Usonbekova

Year Up United — Application Development Capstone Project
