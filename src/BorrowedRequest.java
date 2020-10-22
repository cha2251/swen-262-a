public class BorrowedRequest implements Request{

    /*
    This class is used for string formatting of borrowed books
     */

    private Library library;

    //Instantiates Library in this class
    public BorrowedRequest(Library library) {
        this.library = library;
    }

    //Returns a formatted string of borrowed books from a visitor
    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if(args.length == 2) {
            String visitor = args[1];
            return prefix + library.findBorrowedBooks(visitor);
        }
        return prefix + "visitor ID";
    }

}
