package accounts;

/**
 * Base Account class with basic properties and operations.
 * Features:
 * - accountNumber, accountHolder, balance
 * - deposit, withdraw (throws InsufficientFundsException), transferTo
 * - basic validation and synchronization for thread-safety
 */
public class Account {
    private final String accountNumber;
    private String accountHolder;
    private double balance;

    public Account(String accountNumber, String accountHolder) {
        this(accountNumber, accountHolder, 0.0);
    }

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("accountNumber is required");
        }
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("accountHolder is required");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("initialBalance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    // Simple contract:
    // - deposit: amount > 0
    // - withdraw: amount > 0 and <= balance, otherwise InsufficientFundsException

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
    }

    public synchronized void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds: required=" + amount + ", available=" + balance);
        }
        balance -= amount;
    }

    /**
     * Transfer amount from this account to target account.
     * Uses ordering by accountNumber to avoid deadlocks when two threads transfer in opposite directions.
     */
    public void transferTo(Account target, double amount) throws InsufficientFundsException {
        if (target == null) {
            throw new IllegalArgumentException("target account is required");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (this == target) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        Account first = this.accountNumber.compareTo(target.accountNumber) < 0 ? this : target;
        Account second = first == this ? target : this;

        synchronized (first) {
            synchronized (second) {
                // withdraw will validate funds
                this.withdraw(amount);
                target.deposit(amount);
            }
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        if (accountHolder == null || accountHolder.trim().isEmpty()) {
            throw new IllegalArgumentException("accountHolder is required");
        }
        this.accountHolder = accountHolder;
    }

    public synchronized double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolder='" + accountHolder + '\'' +
                ", balance=" + balance +
                '}';
    }

    /**
     * Simple demonstration main for the Account class.
     * Shows deposit, withdraw (with insufficient funds handling) and transfer.
     */
    public static void main(String[] args) {
        Account a = new Account("A100", "Alice", 1000.0);
        Account b = new Account("B200", "Bob", 300.0);

        System.out.println("[Account.main] Initial:\n  " + a + "\n  " + b);

        a.deposit(200.0);
        System.out.println("[Account.main] After depositing 200 to A: " + a);

        try {
            a.withdraw(1500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("[Account.main] Expected insufficient funds: " + e.getMessage());
        }

        try {
            a.transferTo(b, 500.0);
            System.out.println("[Account.main] After transferring 500 from A to B:");
            System.out.println("  " + a);
            System.out.println("  " + b);
        } catch (InsufficientFundsException e) {
            System.out.println("[Account.main] Transfer failed: " + e.getMessage());
        }

        System.out.println("[Account.main] Final:\n  " + a + "\n  " + b);
    }
}
