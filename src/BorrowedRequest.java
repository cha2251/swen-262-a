public class BorrowedRequest implements Request{

    private Library library;

    //Instantiates Library in this class
    public BorrowedRequest(Library library) {
        this.library = library;
    }

    //Returns a string of the formatted notification someone has arrived.
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
