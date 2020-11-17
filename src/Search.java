import java.util.List;

/**
 * An interface for a strategy that allows searching for books by
 * specific criteria
 */
public interface Search {
    List<RealBook> result(List<RealBook> books);
}
