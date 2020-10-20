import java.util.List;

public class BasicSearch implements Search {

    private String result;
    private Catalog catalog;

    BasicSearch(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public List<Book> result(List<Book> books) {
        return catalog.sortCatalog();
    }
    public String getResult() {
        return result;
    }
}
