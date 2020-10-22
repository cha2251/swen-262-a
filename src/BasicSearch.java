import java.util.List;

public class BasicSearch implements Search {

    /*
    Sorts lists depending on their type
     */

    private Catalog catalog;
    private BookList listType;

    /**
     * Gets the catalog and list that is being used
     * @param catalog
     * @param listType
     */
    BasicSearch(Catalog catalog, BookList listType) {
        this.catalog = catalog;
        this.listType = listType;
    }

    /**
     * Sorts the catalog based on what type of list it is and returns that sorted list
     * @param books
     * @return
     */
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
