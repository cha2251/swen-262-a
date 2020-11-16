public class BorrowedRequest implements Request {

    /*
    This class is used for string formatting of borrowed books
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public BorrowedRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string of borrowed books from a visitor
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String clientID = args[0];
        String prefix = args[1] + ",";
        if (args.length == 3) {
            String visitor = args[2];
            return prefix + library.findBorrowedBooks(visitor,clientID);
        }
        return prefix + "visitor ID";
    }

}
