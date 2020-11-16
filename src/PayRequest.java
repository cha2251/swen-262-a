public class PayRequest implements Request {

    /*
    Used for the string formatting of when a visitor pays a fine
     */

    private Library library;

    /**
     * Instantiates Library in this class
     * @param library
     */
    public PayRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for a visitor payment
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 3) {
            String visitor = args[1];
            String amount = args[2];
            return prefix + library.payFine(visitor, amount);
        }
        return prefix + "visitor ID,amount;";
    }

    /**
     * Undo a Pay Request
     * @param args
     * @return
     */
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 3) {
            String visitor = args[1];
            String amount = args[2];
            return library.undoPayFine(visitor, amount);
        }
        return "Undid Pay Fine Request";
    }
}
