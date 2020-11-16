public class Account {
    private String username;
    private String password;
    private AccountType type;
    private Visitor visitorAccount;
    private String clientID;

    enum AccountType{
        EMPLOYEE,
        VISITOR
    }

    public Account(String username, String password, String type, Visitor visitorAccount, String clientID) {
        this.username = username;
        this.password = password;
        this.type = AccountType.valueOf(type);
        this.visitorAccount = visitorAccount;
        this.clientID = clientID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public Visitor getVisitorAccount() {
        return visitorAccount;
    }

    public void setVisitorAccount(Visitor visitorAccount) {
        this.visitorAccount = visitorAccount;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }
}
