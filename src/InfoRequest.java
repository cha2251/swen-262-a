public class InfoRequest implements Request{

    /*
    this class is used for the formatting book information
     */

    private Library library;

    /**
     Instantiates Library in this class
     @param library
     */
    public InfoRequest(Library library) {
        this.library = library;
    }

    /**
     * Returns a formatted string for information on a book
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String clientID = args[0];
        String prefix = args[1] + ",";
        if (args.length == 7) {
            String title = args[2];
            String authorList = args[3];
            String isbn = args[4];
            String publisher = args[5];
            String sort = args[6];
            return prefix + library.search(title, authorList, isbn, publisher, sort, BookList.OWNED, clientID);
        }
        return prefix + "missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
