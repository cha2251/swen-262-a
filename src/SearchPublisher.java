import java.util.ArrayList;
import java.util.List;

/**
 * Implements a search strategy that finds a book by publisher
 */
public class SearchPublisher extends SearchModifier {

    private String publisher;

    /**
     * Constructor
     * @param search
     * @param publisher
     */
    public SearchPublisher(Search search, String publisher) {
        super(search);
        this.publisher = publisher;
    }

    /**
     * returns a list of books sorted by publisher
     * @param book
     * @return
     */
    public List<RealBook> result(List<RealBook> book) {
        List<RealBook> toReturn = new ArrayList<>();
        List<RealBook> toPrune = super.result(book);
        //User opted for no publisher search
        if (publisher.equals("*")) {
            return toPrune;
        }
        //User wants to search for publisher
        for (RealBook b : toPrune) {
            if (b.publisher.equalsIgnoreCase(publisher)) {
                toReturn.add(b);
            }
        }
        return toReturn;
    }
}
