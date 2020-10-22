import java.util.ArrayList;
import java.util.List;

public class Catalog {

    /*
    This class is used for the creation and management of book catalogs
     */

    private List<Book> purchasableList;
    private List<Book> ownedList;
    private Ordering sortOrder;
    private List sortedList;

    /*
    Creates a new catalog
     */
    public Catalog(){
        purchasableList = new ArrayList<>();
        ownedList = new ArrayList<>();
        sortOrder = new TitleOrdering();
    }

    /*
    Adds a book to the list of purchasable books
     */
    public void addBook(Book book) {
        purchasableList.add(book);
    }

    /*
    adds the specified amount of copies of the book to the list of books you own
     */
    public void buyBook(Book book, int amount){
        if(!ownedList.contains(book)) {
            ownedList.add(book);
        }
        book.addCopies(amount);
    }

    /*
    Checks the list of books you own against a list provided, if you own all own all the books, it returns the full list
    otherwise it returns a list of the ones you do not own.
     */
    public List<?> getBooks(List<String> ids){
        List<Book> passList = new ArrayList<>();
        List<String> failList = new ArrayList<>();
        failList.addAll(ids);
        for (String id : ids) {
            for (Book book : ownedList) {
                if (book.getIsbn() == Long.parseLong(id)) {
                    passList.add(book);
                    failList.remove(id);
                    break;
                }
            }
        }
        if (failList.size() > 0) return failList;
        return passList;
    }

    /*
    Tries to get a list of books from a sorted list, returns either all books not on the list, or a list of every book
    if all were on the list
     */
    public List<?> checkBooks(List<String> ids) {
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
    /*
    Sets our sort order
     */

    public void setSortOrder(Ordering newOrdering) {
        sortOrder = newOrdering;
    }

    /*
    Used to sort the owned list in the sort order
     */
    public List sortCatalog(){
        sortedList = sortOrder.sort(ownedList);
        return sortedList;
    }

    /*
    Used to sort the purchasable list in the sort order
     */
    public List sortPurchasable() {
        return sortOrder.sort(purchasableList);
    }
}
