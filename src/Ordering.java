import java.util.List;

/**
 * Interface for ordering classes
 */
public interface Ordering {

    /*
    This will be the blueprint for sorting functions in our TitleOrdering class
     */
    List sort(List<RealBook> bookList);
}
