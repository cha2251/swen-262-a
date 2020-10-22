import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> purchasableList;
    private List<Book> ownedList;
    private Ordering sortOrder;
    private List sortedList;

    public Catalog(){
        purchasableList = new ArrayList<>();
        ownedList = new ArrayList<>();
        sortOrder = new TitleOrdering();
    }

    public void addBook(Book book) {
        purchasableList.add(book);
    }

    public void buyBook(Book book, int amount){
        if(!ownedList.contains(book)) {
            ownedList.add(book);
        }
        book.addCopies(amount);
    }

    public List<?> getBooks(List<String> ids) {
        List<Book> books = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        for (String bID : ids) {
            try {
                books.add((Book) sortedList.get(Integer.parseInt(bID)));
            } catch (Exception e) {
                failList.add(bID);
            }
        }
        if (failList.size() > 0) return failList;
        return books;
    }

    public void setSortOrder(Ordering newOrdering){
        sortOrder = newOrdering;
    }

    public List sortCatalog(){
        return sortOrder.sort(ownedList);
    }

    public List sortPurchasable() {
        sortedList = sortOrder.sort(purchasableList);
        return sortedList;
    }
}
