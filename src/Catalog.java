import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> purchasableList;
    private List<Book> ownedList;
    private Ordering sortOrder;

    public Catalog(){
        purchasableList = new ArrayList<Book>();
        ownedList = new ArrayList<Book>();
    }

   public void addBook(Book book) {
        purchasableList.add(book);
        System.out.println(book.title + " published by " + book.publisher + " on " + book.publishedDate);
    }

    public void buyBook(Book book){
        ownedList.add(book);
    }

    public void setSortOrder(Ordering newOrdering){
        sortOrder = newOrdering;
    }

    public List sortCatalog(Ordering ordering){
        return sortOrder.sort(ownedList);
    }
}
