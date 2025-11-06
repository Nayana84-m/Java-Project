package accounts;

public class ReportGenerator {
    public static void main(String[] args) {
        AccountDatabase db = new AccountDatabase();
        db.createAccount("ACC001", "John", 1000.0);
        db.createAccount("ACC002", "Alice", 500.0);

        System.out.println("=== ACCOUNT REPORT ===");

        for (Account acc : db.getAllAccounts()) {
            System.out.println("Account ID: " + acc.getAccountNumber());
            System.out.println("Name: " + acc.getAccountHolder());
            System.out.println("Balance: Rs. " + acc.getBalance());
            System.out.println();
        }

        System.out.println("=== SYSTEM SUMMARY ===");
        System.out.println("Total Accounts: " + db.getTotalAccounts());
        System.out.println("Total Balance: Rs. " + db.getTotalBalance());
    }
}
