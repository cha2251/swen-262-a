/**
 *Implements a request for search results
 */
public class SearchRequest implements Request {
    private Library library;

    /**
     * Constructor
     * @param library
     */
    public SearchRequest(Library library) {
        this.library = library;
    }

    /**
     * Breaks up a request string for parsing
     * @param args
     * @return
     */
    @Override
    public String execute(String[] args) {
        String prefix = args[1] + ",";
        if (args.length == 7) {
            String clientID = args[0];
            String title = args[2];
            String authorList = args[3];
            String isbn = args[4];
            String publisher = args[5];
            String sort = args[6];
            return prefix + library.search(title, authorList, isbn, publisher, sort, BookList.PURCHASABLE,clientID);
        }
        return prefix + "missing-parameters,{title,{authors},[isbn, [publisher,[sort order]]]};";
    }
}
