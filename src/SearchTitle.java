import java.util.ArrayList;
import java.util.List;

/**
 *  Implements searching a list of books by title
 */
public class SearchTitle extends SearchModifier {

    private String title;

    /**
     * Constructor
     * @param search
     * @param title
     */
    public SearchTitle(Search search, String title) {
        super(search);
        this.title = title;
    }

    /**
     * returns a list of books sorted by title
     * @param book
     * @return
     */
    public List<Book> result(List<Book> book) {
        List<Book> toReturn = new ArrayList<>();
        List<Book> toPrune = super.result(book);
        //User opted for no title searches
        if(title.equals("*")) {
            return toPrune;
        }
        //User wants to search for title
        for(Book b : toPrune) {
            if(b.title.equalsIgnoreCase(title)) {
                toReturn.add(b);
            }
        }
        return toReturn;
    }
}
