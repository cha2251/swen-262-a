public class BorrowedRequest implements Request {

    private Library library;

    public BorrowedRequest(Library library) {
        this.library = library;
    }

    @Override
    public String execute(String[] args) {
        String prefix = args[0] + ",";
        if (args.length == 2) {
            String visitor = args[1];
            return prefix + library.findBorrowedBooks(visitor);
        }
        return prefix + "visitor ID";
    }

}
