import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Book {
    long isbn;
    String title;
    List<String> authorList;
    String publisher;
    String publishedDate;
    int pages;
    int copies;
    int available;

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

    public String getTitle(){
        return title;
    }

    public long getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return isbn +","+ title +","+authorList.toString()+","+publisher+","+publishedDate +","+ pages +"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn == book.isbn;
    }
}
