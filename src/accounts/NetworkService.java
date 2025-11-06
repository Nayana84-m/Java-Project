package accounts;

public class NetworkService {
    private AccountDatabase db;
    
    public NetworkService(AccountDatabase db) {
        this.db = db;
    }
    
    public String processRequest(String request) {
        String[] parts = request.split(":");
        if (parts.length < 2) {
            return "ERROR: Invalid format";
        }
        
        String command = parts[0];
        String accountId = parts[1];
        
        switch (command) {
            case "BALANCE":
                Account acc = db.getAccount(accountId);
                return acc != null ? "Balance: $" + acc.getBalance() : "Account not found";
                
            case "DEPOSIT":
                if (parts.length < 3) return "Amount required";
                try {
                    double amount = Double.parseDouble(parts[2]);
                    if (db.getAccount(accountId) == null) {
                        db.createAccount(accountId, "User", 0.0);
                    }
                    return db.deposit(accountId, amount) ? "Deposit successful" : "Deposit failed";
                } catch (NumberFormatException e) {
                    return "Invalid amount";
                }
                
            case "WITHDRAW":
                if (parts.length < 3) return "Amount required";
                try {
                    double amount = Double.parseDouble(parts[2]);
                    return db.withdraw(accountId, amount) ? "Withdrawal successful" : "Insufficient funds";
                } catch (NumberFormatException e) {
                    return "Invalid amount";
                }
                
            default:
                return "Unknown command";
        }
    }
    
    public static void main(String[] args) {
        AccountDatabase db = new AccountDatabase();
        db.createAccount("NET001", "Network User", 1000.0);
        
        NetworkService service = new NetworkService(db);
        
        System.out.println("Testing network service:");
        System.out.println(service.processRequest("BALANCE:NET001"));
        System.out.println(service.processRequest("DEPOSIT:NET001:500"));
        System.out.println(service.processRequest("WITHDRAW:NET001:200"));
        System.out.println(service.processRequest("BALANCE:NET001"));
    }
}