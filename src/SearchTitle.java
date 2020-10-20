import java.util.ArrayList;
import java.util.List;

public class SearchTitle extends SearchModifier {

    private String title;
    public SearchTitle(Search search, String title) {
        super(search);
        this.title = title;
    }
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
