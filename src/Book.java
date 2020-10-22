import java.util.Date;

public class Book {

    private int isbn, pageCount, numberOfCopies, copiesAvailable;
    private String title, author, publisher;
    private Date publishDate;

    /*
    Used for the creation of a new book, includes all necessary information on the book
     */
    public Book(int isbn, String title, String author, String publisher, Date publishDate, int pageCount, int numberOfCopies, int copiesAvailable){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.pageCount = pageCount;
        this.numberOfCopies = numberOfCopies;
        this.copiesAvailable = copiesAvailable;
    }
    /*
    When a Book is checked in, this increases the number of copies avaliable of that book by 1
     */
    public void checkIn(){
        copiesAvailable++;
    }

    /*
    When a Book is checked out, this decreases the number of copies avilable of that book by 1
    */
    public void checkOut(){
        copiesAvailable--;
    }

    /*
    Returns a String of the name of the author
     */
    public String getAuthor(){
        return author;
    }
}
