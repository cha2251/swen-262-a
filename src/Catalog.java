import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> purchasableList;
    private List<Book> ownedList;
    private Ordering sortOrder;

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

    public List<?> getBooks(List<String> ids){
        List<Book> passList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        failList.addAll(ids);
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
    public List sortPurchasable() {return sortOrder.sort(purchasableList);}
}
