package accounts;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventHandlers implements ActionListener {
    private AccountDatabase db;
    private String operation;
    private JTextField accountField, amountField;
    private JTextArea resultArea;

    public EventHandlers(AccountDatabase db, String operation, JTextField accountField, JTextField amountField, JTextArea resultArea) {
        this.db = db;
        this.operation = operation;
        this.accountField = accountField;
        this.amountField = amountField;
        this.resultArea = resultArea;
    }

    public void actionPerformed(ActionEvent e) {
        String accountId = accountField.getText();
        String result = "";

        switch (operation) {
            case "DEPOSIT":
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (db.getAccount(accountId) == null) {
                        db.createAccount(accountId, "User", 0.0);
                    }
                    if (db.deposit(accountId, amount)) {
                        result = "Deposit successful! Balance: Rs. " + db.getAccount(accountId).getBalance();
                    } else {
                        result = "Deposit failed!";
                    }
                } catch (NumberFormatException ex) {
                    result = "Invalid amount!";
                }
                break;

            case "WITHDRAW":
                try {
                    double amount = Double.parseDouble(amountField.getText());
                    if (db.withdraw(accountId, amount)) {
                        result = "Withdrawal successful! Balance: Rs. " + db.getAccount(accountId).getBalance();
                    } else {
                        result = "Withdrawal failed! Insufficient funds or account not found.";
                    }
                } catch (NumberFormatException ex) {
                    result = "Invalid amount!";
                }
                break;

            case "BALANCE":
                Account acc = db.getAccount(accountId);
                if (acc != null) {
                    result = "Account: " + acc.getAccountHolder() + "\nBalance: Rs. " + acc.getBalance();
                } else {
                    result = "Account not found!";
                }
                break;
        }

        resultArea.setText(result);
    }

    public static void main(String[] args) {
        AccountDatabase db = new AccountDatabase();
        db.createAccount("TEST001", "Test User", 500.0);
        System.out.println("Event handling test - Account created with balance: Rs. " + db.getAccount("TEST001").getBalance());
    }
}
