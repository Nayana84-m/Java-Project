package NAYANA;

import SNEHA.*;
import java.util.List;

public class ReportGenerator {
    
    public String generateAccountStatement(Account account, List<Transaction> transactions) {
        StringBuilder report = new StringBuilder();
        report.append("=== ACCOUNT STATEMENT ===\n");
        report.append("Account Number: ").append(account.getAccountNumber()).append("\n");
        report.append("Account Holder: ").append(account.getAccountHolderName()).append("\n");
        report.append("Current Balance: $").append(account.getBalance()).append("\n");
        report.append("=== TRANSACTIONS ===\n");
        
        for (Transaction t : transactions) {
            report.append(t.getTimestamp()).append(" - ")
                  .append(t.getType()).append(": $").append(t.getAmount()).append("\n");
        }
        
        return report.toString();
    }
    
    public String generateSummaryReport(AccountDatabase database) {
        StringBuilder report = new StringBuilder();
        report.append("=== BANKING SYSTEM SUMMARY ===\n");
        report.append("Transaction Log:\n");
        
        for (String log : database.getTransactionLog()) {
            report.append(log).append("\n");
        }
        
        return report.toString();
    }
}
