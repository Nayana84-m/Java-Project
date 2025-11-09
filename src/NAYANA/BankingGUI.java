package NAYANA;

import SNEHA.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankingGUI extends JFrame {
    private BankingService bankingService;
    private JTextField accountField, nameField, amountField;
    private JTextArea resultArea;
    
    public BankingGUI() {
        bankingService = new BankingService();
        setupGUI();
    }
    
    private void setupGUI() {
        setTitle("Banking System - SNEHA & NAYANA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        accountField = new JTextField();
        nameField = new JTextField();
        amountField = new JTextField();
        
        inputPanel.add(new JLabel("Account Number:"));
        inputPanel.add(accountField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        
        JButton createBtn = new JButton("Create Account");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");
        
        inputPanel.add(createBtn);
        inputPanel.add(depositBtn);
        inputPanel.add(withdrawBtn);
        inputPanel.add(balanceBtn);
        
        resultArea = new JTextArea(15, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        
        createBtn.addActionListener(e -> createAccount());
        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        balanceBtn.addActionListener(e -> checkBalance());
        
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        
        resultArea.append("Banking System Ready!\n");
        resultArea.append("SNEHA: Core Banking Logic ✓\n");
        resultArea.append("NAYANA: Advanced Features ✓\n\n");
    }
    
    private void createAccount() {
        try {
            Account account = bankingService.openAccount(accountField.getText(), nameField.getText(), "SAVINGS");
            resultArea.append("Account created: " + account.getAccountNumber() + "\n");
        } catch (Exception e) {
            resultArea.append("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void deposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            bankingService.deposit(accountField.getText(), amount);
            resultArea.append("Deposited: $" + amount + "\n");
        } catch (Exception e) {
            resultArea.append("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void withdraw() {
        try {
            String accountNum = accountField.getText();
            double amount = Double.parseDouble(amountField.getText());
            
            double currentBalance = bankingService.getBalance(accountNum);
            if (currentBalance == 0.0) {
                resultArea.append("Error: Account not found or no balance. Create account and deposit first!\n");
                return;
            }
            if (amount > currentBalance) {
                resultArea.append("Error: Insufficient funds. Balance: $" + currentBalance + "\n");
                return;
            }
            
            bankingService.withdraw(accountNum, amount);
            resultArea.append("Withdrawn: $" + amount + " | New Balance: $" + bankingService.getBalance(accountNum) + "\n");
        } catch (Exception e) {
            resultArea.append("Error: " + e.getMessage() + "\n");
        }
    }
    
    private void checkBalance() {
        try {
            double balance = bankingService.getBalance(accountField.getText());
            resultArea.append("Balance: $" + balance + "\n");
        } catch (Exception e) {
            resultArea.append("Error: " + e.getMessage() + "\n");
        }
    }
}
