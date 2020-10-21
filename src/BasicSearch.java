import java.util.List;

public class BasicSearch implements Search {

    private Catalog catalog;
    private BookList listType;

    BasicSearch(Catalog catalog, BookList listType) {
        this.catalog = catalog;
        this.listType = listType;
    }

    @Override
    public List result(List<Book> books) {
        switch (listType) {
            case PURCHASABLE:
                return catalog.sortPurchasable();
            default:
                return catalog.sortCatalog();
        }
    }
}
