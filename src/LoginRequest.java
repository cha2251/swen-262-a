public class LoginRequest implements Request {

    /*
    Used for the string formatting of when a new visitor is registered
     */

    private Library library;

    /**
     * Instantiates Library in this class
     * @param library
     */
    public LoginRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for registering a new visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        if (args.length == 4) {
            String clientID = args[0];
            String username = args[2];
            String pwd = args[3];
            String result = library.login(clientID,username,pwd);
            return result;
        }
        return "client ID,login,username,password;";
    }
}
