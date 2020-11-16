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


    /**
     * Used for the creation of a new book, includes all necessary information on the book
     * @param isbn
     * @param title
     * @param authorList
     * @param publisher
     * @param publishedDate
     * @param pages
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



    /**
     * Increases total copies and amount available of a book by 1
     * @param amount
     */
    public void addCopies(int amount) {
        copies += amount;
        available += amount;
    }


    /**
     * Returns a String of the title of the Book
     * @return
     */
    public String getTitle(){
        return title;
    }


    /**
     * Returns a long of the ISBN# of the book
     * @return
     */
    public long getIsbn() {
        return isbn;
    }

    /**
     * Formatting for default of how to print the info of a book
     * @return
     */
    @Override
    public String toString() {
        return isbn + "," + title + "," + authorList.toString() + "," + publisher + "," + publishedDate + "," + pages + "\n";
    }

    /**
     * How to check equivalnce between books
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn;
    }
    /*
    When a Book is checked in, this increases the number of copies avaliable of that book by 1
     */
    public void checkIn(){
        available++;
    }

    /*
    When a Book is checked out, this decreases the number of copies avilable of that book by 1
    */
    public void checkOut(){
        available--;
    }

    /*
    Returns a String of the name of the author
     */
    public List<String> getAuthors(){
        return authorList;

    }
}
