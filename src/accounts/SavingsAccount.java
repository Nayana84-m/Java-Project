package accounts;

/**
 * SavingsAccount extends Account by adding a simple interest mechanism.
 *
 * Features:
 * - annualInterestRate (e.g., 0.03 for 3% per year)
 * - applyMonthlyInterest(): computes one month's interest and credits it to the account
 *
 * This class deliberately keeps behavior small and safe for teaching purposes.
 */
public class SavingsAccount extends Account {
    // Annual interest rate expressed as a decimal (e.g. 0.03 = 3% APR)
    private double annualInterestRate;

    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance, double annualInterestRate) {
        super(accountNumber, accountHolder, initialBalance);
        setAnnualInterestRate(annualInterestRate);
    }

    public SavingsAccount(String accountNumber, String accountHolder, double annualInterestRate) {
        this(accountNumber, accountHolder, 0.0, annualInterestRate);
    }

    /**
     * Get the annual interest rate (decimal form, e.g. 0.03 = 3%).
     */
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    /**
     * Set the annual interest rate. Must be non-negative.
     */
    public void setAnnualInterestRate(double annualInterestRate) {
        if (annualInterestRate < 0) {
            throw new IllegalArgumentException("annualInterestRate cannot be negative");
        }
        this.annualInterestRate = annualInterestRate;
    }

    /**
     * Apply one month's interest to this account.
     * Interest = currentBalance * (annualInterestRate / 12)
     * This method is synchronized to avoid race conditions on balance.
     *
     * @return the interest amount that was applied
     */
    public double applyMonthlyInterest() {
        double interest;
        synchronized (this) {
            double balance = getBalance();
            interest = balance * (annualInterestRate / 12.0);
            if (interest > 0) {
                deposit(interest);
            }
        }
        return interest;
    }

    @Override
    public String toString() {
        return "SavingsAccount{" +
                "accountNumber='" + getAccountNumber() + '\'' +
                ", accountHolder='" + getAccountHolder() + '\'' +
                ", balance=" + getBalance() +
                ", annualInterestRate=" + annualInterestRate +
                '}';
    }

    /**
     * Demo main for SavingsAccount showing monthly interest application.
     */
    public static void main(String[] args) {
        SavingsAccount s = new SavingsAccount("S300", "Carol", 1000.0, 0.06); // 6% APR
        System.out.println("[SavingsAccount.main] Before interest: " + s);
        double interest = s.applyMonthlyInterest();
        System.out.println("[SavingsAccount.main] Applied monthly interest: " + interest);
        System.out.println("[SavingsAccount.main] After interest: " + s);
    }
}
