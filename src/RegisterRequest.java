public class RegisterRequest implements Request {

    /*
    Used for the string formatting of when a new visitor is registered
     */

    private Library library;

    /**
     * Instantiates Library in this class
     * @param library
     */
    public RegisterRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for registering a new visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        if (args.length == 5) {
            String firstName = args[1];
            String lastName = args[2];
            String address = args[3];
            String phone = args[4];
            //Return error if visitors are duplicate
            String result = library.registerVisitor(firstName, lastName, address, phone);
            return result;
        }
        return "register,missing-parameters,{first name,last name,address, phone-number};";
    }
}
