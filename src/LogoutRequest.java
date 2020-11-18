public class LogoutRequest implements Request {

    /*
    Used for the string formatting of when a new visitor is registered
     */

    private Library library;

    /**
     * Instantiates Library in this class
     * @param library
     */
    public LogoutRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for registering a new visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        if (args.length == 2) {
            String clientID = args[0];
            String result = library.logout(clientID);
            return result;
        }
        return "client ID,login,username,password;";
    }
}
