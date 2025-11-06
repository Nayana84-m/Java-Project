package accounts;

import java.util.ArrayList;
import java.util.List;

public class AccountDatabase {
    private List<Account> accounts = new ArrayList<>();

    public void createAccount(String id, String name, double initialBalance) {
        Account acc = new Account(id, name, initialBalance);
        accounts.add(acc);
    }

    public Account getAccount(String id) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(id)) {
                return acc;
            }
        }
        return null;
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public boolean deposit(String id, double amount) {
        Account acc = getAccount(id);
        if (acc != null) {
            acc.deposit(amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(String id, double amount) {
        Account acc = getAccount(id);
        if (acc != null) {
            try {
                acc.withdraw(amount);
                return true;
            } catch (InsufficientFundsException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return false;
    }

    public double getTotalBalance() {
        double total = 0.0;
        for (Account acc : accounts) {
            total += acc.getBalance();
        }
        return total;
    }

    // ADD THIS METHOD:
    public int getTotalAccounts() {
        return accounts.size();
    }
}
