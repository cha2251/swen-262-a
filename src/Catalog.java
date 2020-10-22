import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> bookList;
    private Ordering sortOrder;

    /*
    Creates a new arraylist of books.
     */
    public Catalog(){
        bookList = new ArrayList<Book>();
    }

    /*
    Sets our sort order
     */
    public void setSortOrder(Ordering newOrdering){
        sortOrder = newOrdering;
    }

    /*
    Used to sort the catalog in the sort order
     */
    public List sortCatalog(Ordering ordering){
        return sortOrder.sort(bookList);
    }
}
