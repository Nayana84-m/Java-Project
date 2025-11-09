package NAYANA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class EventHandlers {
    
    public static ActionListener createAccountHandler(Runnable action) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    action.run();
                    showAlert("Success", "Account created successfully!");
                } catch (Exception ex) {
                    showAlert("Error", "Failed to create account: " + ex.getMessage());
                }
            }
        };
    }
    
    public static ActionListener transactionHandler(Runnable action) {
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    action.run();
                    showAlert("Success", "Transaction completed successfully!");
                } catch (Exception ex) {
                    showAlert("Error", "Transaction failed: " + ex.getMessage());
                }
            }
        };
    }
    
    private static void showAlert(String title, String message) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
}
