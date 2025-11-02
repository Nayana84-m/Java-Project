package accounts;

/**
 * Small test harness for Account to verify deposit/withdraw/transfer behavior.
 */
public class TestAccount {
    public static void main(String[] args) {
        Account a = new Account("A100", "Alice", 1000.0);
        Account b = new Account("B200", "Bob", 500.0);

        System.out.println("Initial: " + a + " | " + b);

        a.deposit(200.0);
        System.out.println("After deposit to A: " + a);

        try {
            a.withdraw(1500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("Expected insufficient funds when withdrawing 1500 from A: " + e.getMessage());
        }

        try {
            a.transferTo(b, 500.0);
            System.out.println("After transfer 500 from A to B:");
            System.out.println("  A: " + a);
            System.out.println("  B: " + b);
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }

        // Demonstrate SavingsAccount monthly interest
        SavingsAccount s = new SavingsAccount("S300", "Carol", 1000.0, 0.06); // 6% APR
        System.out.println("Before interest: " + s);
        double interest = s.applyMonthlyInterest();
        System.out.println("Applied monthly interest: " + interest);
        System.out.println("After interest: " + s);

        // Final balances
        System.out.println("Final: " + a + " | " + b + " | " + s);
    }
}
