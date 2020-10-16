import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> bookList;
    private Ordering sortOrder;

    public Catalog(){
        bookList = new ArrayList<Book>();
    }

    public void setSortOrder(Ordering newOrdering){
        sortOrder = newOrdering;
    }

    public List sortCatalog(Ordering ordering){
        return sortOrder.sort(bookList);
    }
}
