import java.util.ArrayList;
import java.util.List;

/**
 * Implements a search strategy that finds books by author
 */
public class SearchAuthor extends SearchModifier {
    private String authors;

    /**
     * Constructor
     * @param search
     * @param authors
     */
    public SearchAuthor(Search search, String authors) {
        super(search);
        this.authors = authors;
    }

    /**
     * returns a list of books by a given author
     * @param book
     * @return
     */
    public List<Book> result(List<Book> book) {
        List<Book> toReturn = new ArrayList<>();
        List<Book> toPrune = super.result(book);
        //User opted for no title searches
        if (authors.equals("*")) {
            return toPrune;
        }
        String fixed = authors.replace("{", "").replace("}", "");
        String[] tentativeList = fixed.split(",");
        //User wants to search based on authors
        for (Book b : toPrune) {
            for (String author : tentativeList) {
                if (b.authorList.contains(author)) {
                    toReturn.add(b);
                }
            }
        }
        return toReturn;
    }
}
