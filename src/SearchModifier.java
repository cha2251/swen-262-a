import java.util.List;

public class SearchModifier implements Search {


    protected Search search;

    public SearchModifier(Search search) {
        this.search = search;
    }

    @Override
    public List<Book> result(List<Book> books) {
        return search.result(books);
    }
}
