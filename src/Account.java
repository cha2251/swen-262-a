public abstract class Account {
    String username;
    String password;
    AccountType type;

    enum AccountType{
        EMPLOYEE,
        VISITOR
    }
}
