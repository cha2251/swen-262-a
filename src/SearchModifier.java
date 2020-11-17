import java.util.List;

/**
 * Runs searches on lists of books
 */
public class SearchModifier implements Search {


    protected Search search;

    /**
     * Constructor
     * @param search
     */
    public SearchModifier(Search search) {
        this.search = search;
    }

    /**
     * Runs the specified search on a given list of books
     * @param books
     * @return
     */
    @Override
    public List<RealBook> result(List<RealBook> books) {
        return search.result(books);
    }
}
