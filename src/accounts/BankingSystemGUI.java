package accounts;

import javax.swing.*;
import java.awt.*;

public class BankingSystemGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BankingSystemGUI().createAndShowGUI();
        });
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        // === Backend ===
        AccountDatabase db = new AccountDatabase();

        // === UI Components ===
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        JLabel lblAccount = new JLabel("Account ID:");
        JTextField txtAccount = new JTextField();

        JLabel lblAmount = new JLabel("Amount:");
        JTextField txtAmount = new JTextField();

        JButton btnDeposit = new JButton("Deposit");
        JButton btnWithdraw = new JButton("Withdraw");
        JButton btnBalance = new JButton("Check Balance");

        JTextArea txtResult = new JTextArea(6, 25);
        txtResult.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtResult);

        panel.add(lblAccount);
        panel.add(txtAccount);
        panel.add(lblAmount);
        panel.add(txtAmount);
        panel.add(btnDeposit);
        panel.add(btnWithdraw);
        panel.add(btnBalance);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(scroll, BorderLayout.SOUTH);

        btnDeposit.addActionListener(new EventHandlers(db, "DEPOSIT", txtAccount, txtAmount, txtResult));
        btnWithdraw.addActionListener(new EventHandlers(db, "WITHDRAW", txtAccount, txtAmount, txtResult));
        btnBalance.addActionListener(new EventHandlers(db, "BALANCE", txtAccount, txtAmount, txtResult));

        frame.setVisible(true);
    }
}
