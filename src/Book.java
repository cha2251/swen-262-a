import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Book {

    /*
    this class is used for the creation and management of books
     */

    long isbn;
    String title;
    List<String> authorList;
    String publisher;
    String publishedDate;
    int pages;
    int copies = 0;
    int available = 0;

    /*
    Used for the creation of a new book, includes all necessary information on the book
     */
    public Book(long isbn, String title, String authorList, String publisher, String publishedDate, int pages) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pages = pages;
        String improved = authorList.replace("{", "").replace("}", "");
        this.authorList = new ArrayList<>();
        String[] tentativeList = improved.split(",");
        this.authorList.addAll(Arrays.asList(tentativeList));
    }

    /*
    Increases total copies and amount available of a book by 1
     */
    public void addCopies(int amount) {
        copies+=amount;
        available+=amount;
    }
    /*
    Returns a String of the title of the Book
     */
    public String getTitle(){
        return title;
    }

    /*
    Returns a long of the ISBN# of the book
    */
    public long getIsbn() {
        return isbn;
    }

    /*
    Formatting for default of how to print the info of a book
     */
    @Override
    public String toString() {
        return isbn +","+ title +","+authorList.toString()+","+publisher+","+publishedDate +","+ pages +"\n";
    }

    /*
    How to check equivalnce between books
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn;
    }
}
