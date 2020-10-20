import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> purchasableList;
    private List<Book> ownedList;
    private Ordering sortOrder;

    public Catalog(){
        purchasableList = new ArrayList<Book>();
        ownedList = new ArrayList<Book>();
        sortOrder = new TitleOrdering();
    }

   public void addBook(Book book) {
        purchasableList.add(book);
        ownedList.add(book);
        System.out.println(book.title + " published by " + book.publisher + " on " + book.publishedDate);
    }

    public void buyBook(Book book){
        ownedList.add(book);
    }

    public List<?> getBooks(List<String> ids){
        List<Book> passList = new ArrayList<Book>();
        List<String> failList = ids;
        for (String id : ids) {
            for (Book book : ownedList) {
                if (book.getIsbn() == Long.parseLong(id)){
                    passList.add(book);
                    failList.remove(id);
                    break;
                }
            }
        }
        if(failList.size() > 0) return failList;
        return passList;
    }

    public void setSortOrder(Ordering newOrdering){
        sortOrder = newOrdering;
    }

    public List sortCatalog(){
        return sortOrder.sort(ownedList);
    }
}
