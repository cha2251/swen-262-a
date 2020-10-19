import java.util.Comparator;
import java.util.List;

public class TitleOrdering implements Ordering {


    @Override
    public List sort(List<Book> bookList) {
        bookList.sort(new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getAuthor().compareTo(o2.getAuthor());
            }
        });

        return bookList;
    }
}
