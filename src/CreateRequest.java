public class CreateRequest implements Request {

    /*
    Used for the string formatting of when a new visitor is registered
     */

    private Library library;

    /**
     * Instantiates Library in this class
     * @param library
     */
    public CreateRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for registering a new visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        if (args.length == 6) {
            String clientID = args[0];
            String username = args[2];
            String pwd = args[3];
            String role = args[4].toUpperCase();
            String visID = args[5];
            //Return error if visitors are duplicate
            String result = library.createAccount(clientID, username, pwd, role,visID);
            return result;
        }
        return "client ID,create,username,password,role,visitor ID;";
    }
}
