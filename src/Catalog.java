import java.util.ArrayList;
import java.util.List;

public class Catalog {

    private List<Book> purchasableList;
    private List<Book> ownedList;
    private Ordering sortOrder;
    private List sortedList;

    public Catalog() {
        purchasableList = new ArrayList<>();
        ownedList = new ArrayList<>();
        sortOrder = new TitleOrdering();
    }

    public void addBook(Book book) {
        purchasableList.add(book);
    }

    public void buyBook(Book book, int amount) {
        if (!ownedList.contains(book)) {
            ownedList.add(book);
        }
        book.addCopies(amount);
    }

    public List<?> getBooks(List<String> ids) {
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

    public void setSortOrder(Ordering newOrdering) {
        sortOrder = newOrdering;
    }

    public List sortCatalog() {
        sortedList = sortOrder.sort(ownedList);
        return sortedList;
    }

    public List sortPurchasable() {
        return sortOrder.sort(purchasableList);
    }
}
