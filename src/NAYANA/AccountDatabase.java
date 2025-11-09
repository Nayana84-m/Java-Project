package NAYANA;

import SNEHA.Account;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDatabase {
    private Map<String, Account> accounts;
    private List<String> transactionLog;
    private Set<String> activeAccounts;
    
    public AccountDatabase() {
        this.accounts = new ConcurrentHashMap<>();
        this.transactionLog = Collections.synchronizedList(new ArrayList<>());
        this.activeAccounts = Collections.synchronizedSet(new HashSet<>());
    }
    
    public void storeAccount(Account account) {
        accounts.put(account.getAccountNumber(), account);
        activeAccounts.add(account.getAccountNumber());
    }
    
    public Account retrieveAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    public void logTransaction(String transaction) {
        transactionLog.add(transaction);
    }
    
    public List<String> getTransactionLog() {
        return new ArrayList<>(transactionLog);
    }
}
