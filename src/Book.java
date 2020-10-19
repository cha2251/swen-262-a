import java.util.ArrayList;
import java.util.List;

public class Book {
    long isbn;
    String title;
    List<String> authorList;
    String publisher;
    String publishedDate;
    int pages;
    int copies;
    int available;

    public Book(long isbn, String title, String authorList, String publisher, String publishedDate,
                int pages) {
        this.isbn = isbn;
        this.title = title;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.pages = pages;
        authorList.replace("{", "");
        authorList.replace("}", "");
        this.authorList = new ArrayList<>();
        String[] tentativeList = authorList.split(",");
        for(String author : tentativeList) {
            this.authorList.add(author);
        }
    }

    public String getTitle(){
        return title;
    }
}
