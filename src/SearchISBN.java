import java.util.ArrayList;
import java.util.List;

/**
 * Implements a search strategy that finds a book by ISBN
 */
public class SearchISBN extends SearchModifier {

    private String ISBN;

    /**
     * Constructor
     * @param search
     * @param ISBN
     */
    public SearchISBN(Search search, String ISBN) {
        super(search);
        this.ISBN = ISBN;
    }

    /**
     * returns all books with the given ISBN
     * @param book
     * @return
     */
    public List<RealBook> result(List<RealBook> book) {
        List<RealBook> toReturn = new ArrayList<>();
        List<RealBook> toPrune = super.result(book);
        //User opted for no ISBN search
        if (ISBN.equals("*")) {
            return toPrune;
        }
        //User wants to search for ISBN
        for (RealBook b : toPrune) {
            if (b.isbn == Long.parseLong(ISBN)) {
                toReturn.add(b);
                break;
            }
        }
        return toReturn;
    }
}
