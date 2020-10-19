import java.util.Date;

public class Book {

    private int isbn, pageCount, numberOfCopies, copiesAvailable;
    private String title, author, publisher;
    private Date publishDate;

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

    public void checkIn(){
        copiesAvailable++;
    }

    public void checkOut(){
        copiesAvailable--;
    }

    public String getAuthor(){
        return author;
    }
}
