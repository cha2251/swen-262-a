import java.util.Comparator;
import java.util.List;

/**
 * Implements an ordering by the title of a book.
 */
public class TitleOrdering implements Ordering {


    /**
     * sorts a list of books by title
     * @param bookList
     * @return
     */
    @Override
    public List sort(List<RealBook> bookList) {
        bookList.sort(new Comparator<RealBook>() {
            @Override
            public int compare(RealBook o1, RealBook o2) {
                return o1.getTitle().compareTo(o2.getTitle());
            }
        });

        return bookList;
    }
}
