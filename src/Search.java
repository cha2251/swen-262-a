import java.util.List;

/**
 * An interface for a strategy that allows searching for books by
 * specific criteria
 */
public interface Search {
    List<Book> result(List<Book> books);
}
